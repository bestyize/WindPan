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
import java.nio.file.Files;
import java.util.*;

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
                        f.getName(),
                        f.getAbsolutePath()
                );
                localFileBeanList.add(bean);
            }
        }
        Collections.sort(localFileBeanList, (o1, o2) -> {
            if(o2.getFileType().equals("dir")&&!o1.getFileType().equals("dir")){
                return 1;
            }else if(!o2.getFileType().equals("dir")&&o1.getFileType().equals("dir")){
                return -1;
            }else if(o2.getFileType().equals("dir")&&o1.getFileType().equals("dir")||(!o2.getFileType().equals("dir")&&!o1.getFileType().equals("dir"))){
                return o1.getFileName().compareTo(o2.getFileName());
            }
            return 0;
        });
        return new ResponseEntity<>(localFileBeanList, HttpStatus.OK);
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
            response.setHeader("Content-Disposition","attachment;filename="+file.getName().replaceAll(" ",""));
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



}
