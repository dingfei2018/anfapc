package com.supyuan.util.typechange;

/**
 * @Author: chenan
 * @Description:
 * @Date: Created in 2018/3/15
 */
public class TypeChange {
    /**
     * 货物单位转换
     * @param type
     * @return
     */
    public static String productUnitChange(int type){
        String unitName="";
        switch (type){
            case 1:unitName="散装";
                break;
            case 2:unitName="捆装";
                break;
            case 3:unitName="袋装";
                break;
            case 4:unitName="箱装";
                break;
            case 5:unitName="桶装";
                break;
            default:
                break;
        }
        return unitName;

    }

    /**
     * 回单要求类型转换
     * @param type
     * @return
     */
    public static String receiptRequireChange(int type){
        String require="";
        switch (type){
            case 1:require="回单";
                break;
            case 2:require="电子回单";
                break;
            case 3:require="原单";
                break;
            case 4:require="收条";
                break;
            case 5:require="信封";
                break;
            default:
                break;
        }
        return require;

    }

    /**
     * 送货方式类型转换
     * @param type
     * @return
     */
    public static String deliveryMethodChange(int type){
        String delivery="";
        switch (type){
            case 1:delivery="送货上门";
                break;
            case 2:delivery="自提";
                break;
            case 3:delivery="送货上楼";
                break;
            case 4:delivery="送货卸货";
                break;
            default:
                break;
        }
        return delivery;

    }


    /**
     * 付款方式类型转换
     * @param type
     * @return
     */
    public static String payWayChange(int type){
        String payWay="";
        switch (type){
            case 1:payWay="现付";
                break;
            case 2:payWay="提付";
                break;
            case 3:payWay="回单付";
                break;
            case 4:payWay="月结";
                break;
            case 5:payWay="短欠";
                break;
            case 6:payWay="货款付";
                break;
            case 7:payWay="多笔付";
                break;
            default:
                break;
        }
        return payWay;

    }

}
