package spring.aop;

public class UpdateOrderConvert implements Convert<UpdateOrder> {

    @Override
    public OperateLogDO convert(UpdateOrder updateOrder) {
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOrderId(updateOrder.getUpdateId());
        return operateLogDO;
    }
}
