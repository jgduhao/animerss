package jgduhao.github.io.animerss.enums;

import org.springframework.http.HttpStatus;

public enum ErrorMsg {

    CategoryNotExists("分类不存在", HttpStatus.NOT_FOUND),
    RssFeedNotExists("RSS订阅源不存在", HttpStatus.NOT_FOUND),
    NetConnectError("网络连接失败，请检查网络或代理", HttpStatus.BAD_GATEWAY),
    RssFeedGetError("RSS订阅源数据获取失败，请检查订阅源链接、网络或代理", HttpStatus.BAD_GATEWAY),
    RssFeedParseError("RSS订阅源数据解析失败，请检查订阅源内容、网络或代理", HttpStatus.INTERNAL_SERVER_ERROR),
    TorrentDownloadError("种子文件获取失败，请检查种子连接、网络或代理", HttpStatus.BAD_GATEWAY),
    Aria2UrlError("aria2地址有误，请检查aria2地址配置", HttpStatus.BAD_GATEWAY),
    Aria2RpcError("aria2调用失败，请检查aria2地址以及密钥配置，详情请查看日志", HttpStatus.INTERNAL_SERVER_ERROR),
    AnimeNotExists("剧集信息不存在", HttpStatus.NOT_FOUND),
    AnimeDownloadUrlNotExists("此剧集不包含下载链接，无法下载", HttpStatus.NOT_FOUND),
    AnimeUnsupportedUrl("链接不是torrent种子或magnet磁力链接，暂不支持下载", HttpStatus.BAD_REQUEST),
    Success("操作成功", HttpStatus.OK),
    InnerError("内部错误", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String errorMsg;
    private final HttpStatus httpStatus;

    private ErrorMsg(String errorMsg, HttpStatus status) {
        this.errorMsg = errorMsg;
        this.httpStatus = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
