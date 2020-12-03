package xyz.thewind.windpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thewind.windpan.bean.CommonResp;
import xyz.thewind.windpan.config.CommonConfig;

import java.io.File;

@RestController
public class FileManageController {
    @Autowired
    private CommonConfig commonConfig;
    @RequestMapping("/newfolder")
    public ResponseEntity<CommonResp> createFolder(String currPath,String foldName){
        CommonResp commonResp=new CommonResp();
        if(foldName==null||foldName.equals("undefined")){
            commonResp.setCode(-1);
            commonResp.setModule("newFolder");
            commonResp.setMsg("创建文件夹失败");
        }else {
            if(currPath==null||currPath.equals("home")){
                currPath=commonConfig.localFilePath;
            }
            File file=new File(currPath+foldName);
            if(!file.mkdirs()){
                commonResp.setCode(-1);
                commonResp.setModule("newFolder");
                commonResp.setMsg("创建文件夹失败");
            }
        }
        return new ResponseEntity<>(commonResp, HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<CommonResp> deleteFolder(String currPath,String fileName){
        CommonResp commonResp=new CommonResp();
        if(fileName==null||fileName.equals("undefined")){
            commonResp.setCode(-1);
            commonResp.setModule("deleteFolder");
            commonResp.setMsg("删除失败");
        }else {
            if(currPath==null||currPath.equals("home")){
                currPath=commonConfig.localFilePath;
            }
            File file=new File(currPath+fileName);
            if(!file.delete()){
                commonResp.setCode(-1);
                commonResp.setModule("deleteFolder");
                commonResp.setMsg("删除失败");
            }
        }
        return new ResponseEntity<>(commonResp, HttpStatus.OK);
    }


}
