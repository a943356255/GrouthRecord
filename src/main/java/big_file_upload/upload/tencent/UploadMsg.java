package big_file_upload.upload.tencent;

public class UploadMsg {

    public int status;
    public String msg;
    public String path;

    public UploadMsg() {
        super();
    }

    public UploadMsg (int status, String msg, String path){
        this.status = status;
        this.msg = msg;
        this.path = path;
    }
}
