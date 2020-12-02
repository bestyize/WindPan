package xyz.thewind.windpan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import xyz.thewind.windpan.bean.LocalFileBean;


import java.util.List;
@PropertySource(value = {"classpath:common.properties","classpath:application.properties"})
@Service
public class LocalFileServices {
    private final JdbcTemplate jdbcTemplate;
    @Value ("${sql.query_local_file_list}")
    private String queryLocalFileList;
    @Value("${sql.add_file_to_local}")
    private String addFileToLocal;

    public LocalFileServices(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addFileToLocal(LocalFileBean localFile){
        return jdbcTemplate.update(addFileToLocal,localFile.getFileId()
                ,localFile.getFileName()
                ,localFile.getUploadTime()
                ,localFile.getFileSize()
                ,localFile.getFileType()
                ,localFile.getFileDesc()
                ,localFile.getRealPath())>0;
    }


    public List<LocalFileBean> queryLocalFileList(){
        return jdbcTemplate.query(queryLocalFileList, (rs, rowNum) -> new LocalFileBean(
                rs.getString("fileId"),
                rs.getString("fileName"),
                rs.getLong("uploadTime"),
                rs.getLong("fileSize"),
                rs.getString("fileType"),
                rs.getString("fileDesc"),
                rs.getString("realPath")
        ));
    }
}
