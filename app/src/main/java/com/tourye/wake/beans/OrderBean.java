package com.tourye.wake.beans;

/**
 * Created by longlongren on 2018/8/30.
 * <p>
 * introduce:报名支付订单
 */

public class OrderBean {

    /**
     * status : 0
     * timestamp : 1535592935
     * data : {"serial_number":"235be6c46c5a55a8b580ce9d8f8540b5","parameters":"app_id=2018082161152087&method=alipay.trade.app.pay&format=JSON&charset=utf-8&sign_type=RSA2&version=1.0&notify_url=http%3A%2F%2Fapi.wake-test.xorout.com%2Fexternal%2Fali%2Fpayment_notify&timestamp=2018-08-30+09%3A35%3A35&sign=PynSMnXICsICQaP6aq1k%2BqxZN9EjaKs0ViuUPPBzeo0ofigR8plCs%2Blmx5IxpGOZtPeUhF158f1mvzAIbMakiJ7CQZ4AU5o7iC8Nfj8Mprn54TJ%2FMHSabw%2BJoaRkQRVmn3F5PB2BXllmQUKSlgAZXEHRM9x5seC%2Bprli6jpu8Jvkx4WAlwRlVz27CITmuFvIo%2Bp7%2By%2BxkGM4b4ZWnbXzFZi5mB%2FqTB4Oc3t%2BtpUPhZmiroyJudiR9CLodWsx0EDzikbkpt4nZvdVN2qtiUSu59swTMkVhUDrZ9raMRFIxvHAofWySvG7%2FFQdclt8QM2uN39AFe8Fb6v5obG4PeHUcw%3D%3D&biz_content=%7B%22out_trade_no%22%3A%228a3fb6f49b444205ba953942fd64c228%22%2C%22total_amount%22%3A10%2C%22subject%22%3A%22%5Cu4e0d%5Cu8d77%5Cu5c31%5Cu51fa%5Cu5c40%5Cu6311%5Cu6218%5Cu91d1%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D"}
     */

    private int status;
    private int timestamp;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * serial_number : 235be6c46c5a55a8b580ce9d8f8540b5
         * parameters : app_id=2018082161152087&method=alipay.trade.app.pay&format=JSON&charset=utf-8&sign_type=RSA2&version=1.0&notify_url=http%3A%2F%2Fapi.wake-test.xorout.com%2Fexternal%2Fali%2Fpayment_notify&timestamp=2018-08-30+09%3A35%3A35&sign=PynSMnXICsICQaP6aq1k%2BqxZN9EjaKs0ViuUPPBzeo0ofigR8plCs%2Blmx5IxpGOZtPeUhF158f1mvzAIbMakiJ7CQZ4AU5o7iC8Nfj8Mprn54TJ%2FMHSabw%2BJoaRkQRVmn3F5PB2BXllmQUKSlgAZXEHRM9x5seC%2Bprli6jpu8Jvkx4WAlwRlVz27CITmuFvIo%2Bp7%2By%2BxkGM4b4ZWnbXzFZi5mB%2FqTB4Oc3t%2BtpUPhZmiroyJudiR9CLodWsx0EDzikbkpt4nZvdVN2qtiUSu59swTMkVhUDrZ9raMRFIxvHAofWySvG7%2FFQdclt8QM2uN39AFe8Fb6v5obG4PeHUcw%3D%3D&biz_content=%7B%22out_trade_no%22%3A%228a3fb6f49b444205ba953942fd64c228%22%2C%22total_amount%22%3A10%2C%22subject%22%3A%22%5Cu4e0d%5Cu8d77%5Cu5c31%5Cu51fa%5Cu5c40%5Cu6311%5Cu6218%5Cu91d1%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D
         */

        private String serial_number;
        private String parameters;

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public String getParameters() {
            return parameters;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }
    }
}
