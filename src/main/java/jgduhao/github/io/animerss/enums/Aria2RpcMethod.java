package jgduhao.github.io.animerss.enums;

public enum Aria2RpcMethod {

    addTorrent("aria2.addTorrent"),
    addUri("aria2.addUri");

    String methodName;

    Aria2RpcMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName(){
        return methodName;
    }
}
