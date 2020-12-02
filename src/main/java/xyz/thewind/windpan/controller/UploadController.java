package xyz.thewind.windpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.thewind.windpan.bean.CommonResp;
import xyz.thewind.windpan.bean.Token;
import xyz.thewind.windpan.config.CommonConfig;
import xyz.thewind.windpan.util.CookieHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

@RestController
public class UploadController {

    @Autowired
    private CommonConfig commonConfig;
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResponseEntity<CommonResp> uploadFile(MultipartFile[] files,String path, HttpServletRequest request)throws Exception{
        Token token= CookieHelper.parseToken(request.getParameter("Cookie"));
        CommonResp commonResp=new CommonResp();
        if(files==null||files.length==0){
            commonResp.setCode(404);
            commonResp.setModule("upload");
            commonResp.setMsg("未选择文件");
        }else {
            for (MultipartFile file:files){
                String fileDir=commonConfig.localFilePath;
                String fileName=file.getOriginalFilename();
                File fd=new File(fileDir);
                fd.mkdirs();
                File dest=null;
                if(path!=null){
                    path=URLDecoder.decode(path,"UTF-8");
                    File pathFile=new File(path);
                    if(pathFile.isDirectory()){
                        dest=new File(path+File.separator+fileName);
                    }
                }
                if(dest==null){
                    dest=new File(fileDir+fileName);
                }
                file.transferTo(dest);
            }
            commonResp.setCode(0);
            commonResp.setModule("upload");
            commonResp.setMsg("上传成功");
        }
        return new ResponseEntity<>(commonResp, HttpStatus.OK);

    }
}
