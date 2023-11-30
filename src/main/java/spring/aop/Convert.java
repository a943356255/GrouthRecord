package spring.aop;

public interface Convert<PARAM> {

    OperateLogDO convert(PARAM param);

}
