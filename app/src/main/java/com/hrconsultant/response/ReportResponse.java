package com.hrconsultant.response;

public class ReportResponse {


    /**
     * Data : {"Id":1,"Name":"admin"}
     * Status : 0
     */

    private DataBean Data;
    private int Status;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public static class DataBean {
        /**
         * Id : 1
         * Name : admin
         */


        private String Msg;


        public String getName() {
            return Msg;
        }

        public void setName(String Name) {
            this.Msg = Name;
        }
    }


}
