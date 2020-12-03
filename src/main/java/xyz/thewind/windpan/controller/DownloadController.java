package xyz.thewind.windpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thewind.windpan.bean.CommonResp;
import xyz.thewind.windpan.bean.DownloadBean;
import xyz.thewind.windpan.bean.LocalFileBean;
import xyz.thewind.windpan.config.CommonConfig;
import xyz.thewind.windpan.util.EncodeHelper;
import xyz.thewind.windpan.util.FileTypeHelper;


import javax.imageio.stream.FileCacheImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class DownloadController {
    @Autowired
    private CommonConfig commonConfig;

    @RequestMapping("/home")
    public ResponseEntity<List<LocalFileBean>> getCloudFileList(String dirPath){

        File fileDir=new File(commonConfig.localFilePath);
        if(dirPath!=null){
            fileDir=new File(dirPath);
        }
        File[] files=fileDir.listFiles();
        List<LocalFileBean> localFileBeanList=new ArrayList<>();
        if(files!=null){
            for(File f:files){
                LocalFileBean bean=new LocalFileBean(
                        EncodeHelper.md5(f.getName()),
                        f.getName(),
                        f.lastModified(),
                        f.length(),
                        FileTypeHelper.getFileType(f),
                        "",
                        f.getAbsolutePath()
                );
                localFileBeanList.add(bean);
            }
        }
        return new ResponseEntity<>(sortFileList(localFileBeanList), HttpStatus.OK);
    }
    @RequestMapping("/download")
    public ResponseEntity<CommonResp> download(HttpServletResponse response, String realPath){
        File file=new File(realPath);
        CommonResp commonResp=new CommonResp("download");
        if(!file.exists()){
            commonResp.setCode(404);
            commonResp.setModule("download");
            commonResp.setMsg("文件不存在");
        }else {
            response.reset();
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentLengthLong(file.length());
            response.setHeader("Content-Disposition","attachment;filename="+file.getName().replaceAll(" ","_"));
            try(BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file))) {
                byte[] b=new byte[4096];
                OutputStream os= response.getOutputStream();
                int len;
                while ((len=bis.read(b))!=-1){
                    os.write(b,0,len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(commonResp,HttpStatus.OK);
    }

    @RequestMapping("/search")
    public ResponseEntity<List<LocalFileBean>> search(String path,String keyword)throws Exception{
        if(path==null||path.equals("home")){
            path= URLEncoder.encode(commonConfig.localFilePath,"utf-8");
        }
        if(keyword!=null){
            keyword=URLDecoder.decode(keyword,"utf-8");
        }
        File file=new File(URLDecoder.decode(path,"utf-8"));
        List<LocalFileBean> localFileBeanList=new ArrayList<>();
        CommonResp commonResp=new CommonResp("search");
        if(!file.exists()){
            commonResp.setCode(404);
            commonResp.setModule("search");
            commonResp.setMsg("文件不存在");
        }else {
            List<String> fileList=getFileList(file,new ArrayList<>());
            List<String> patternList=fuzzSearch(keyword,fileList);
            File []files=new File[patternList.size()];
            for (int i=0;i<files.length;i++){
                files[i]=new File(patternList.get(i));
            }

            for(File f:files){
                LocalFileBean bean=new LocalFileBean(
                        EncodeHelper.md5(f.getName()),
                        f.getName(),
                        f.lastModified(),
                        f.length(),
                        FileTypeHelper.getFileType(f),
                        FileTypeHelper.getOffPath(f.getAbsolutePath(),commonConfig.localFilePath,f.getName()),
                        f.getAbsolutePath()
                );
                localFileBeanList.add(bean);
            }
        }
        return new ResponseEntity<>(sortFileList(localFileBeanList), HttpStatus.OK);
    }

    private List<LocalFileBean> sortFileList(List<LocalFileBean> list){
        list.sort((o1, o2) -> {
            if (o2.getFileType().equals("dir") && !o1.getFileType().equals("dir")) {
                return 1;
            } else if (!o2.getFileType().equals("dir") && o1.getFileType().equals("dir")) {
                return -1;
            } else if (o2.getFileType().equals("dir") && o1.getFileType().equals("dir") || (!o2.getFileType().equals("dir") && !o1.getFileType().equals("dir"))) {
                return o1.getFileName().compareTo(o2.getFileName());
            }
            return 0;
        });
        return list;
    }

    private List<String> getFileList(File file,List<String> fileList){
        File[] files = file.listFiles();
        if(files==null)return fileList;// 判断目录下是不是空的
        for (File f : files) {
            if(f.isDirectory()){// 判断是否文件夹
                fileList.add(f.getPath());
                getFileList(f,fileList);// 调用自身,查找子目录
            }else
                fileList.add(f.getPath());
        }
        return fileList;
    }

    private List<String> fuzzSearch(String name,List<String> originList){
        List<String> result=new ArrayList<>();
        Pattern pattern=Pattern.compile(name,Pattern.CASE_INSENSITIVE);
        for (String word:originList){
            Matcher matcher= pattern.matcher(word);
            if(matcher.find()){
                result.add(word);
            }
        }
        return result;
    }






}
