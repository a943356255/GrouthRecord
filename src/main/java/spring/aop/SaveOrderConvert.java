package spring.aop;

public class SaveOrderConvert implements Convert<SaveOrder> {

    @Override
    public OperateLogDO convert(SaveOrder saveOrder) {
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOrderId(saveOrder.getSaveId());
        return operateLogDO;
    }
}
