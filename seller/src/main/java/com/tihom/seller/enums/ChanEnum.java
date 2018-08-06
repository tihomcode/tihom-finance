package com.tihom.seller.enums;

/**
 * 渠道配置信息
 * @author TiHom
 * create at 2018/8/5 0005.
 */
public enum ChanEnum {
    ABC("111","ABC","/opt/ABC");
    private String chanId;  //渠道id
    private String chanName;  //渠道名称

    private String ftpPath,ftpUser,ftpPassword;  //ftp传递的路径和用户名密码

    private String rootDir;  //根目录，即所在盘

    ChanEnum(String chanId, String chanName, String rootDir) {
        this.chanId = chanId;
        this.chanName = chanName;
        this.rootDir = rootDir;
    }

    /**
     * 根据渠道编号获取渠道配置
     * @param chanId
     * @return
     */
    public static ChanEnum getByChanId(String chanId){
        for(ChanEnum chanEnum : ChanEnum.values()){
            if(chanEnum.getChanId().equals(chanId)){
                return chanEnum;
            }
        }
        return null;
    }

    public String getChanId() {
        return chanId;
    }

    public String getChanName() {
        return chanName;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public String getRootDir() {
        return rootDir;
    }
}
