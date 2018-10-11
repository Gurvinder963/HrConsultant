package com.hrconsultant.response;

public class LoginResponse {


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

        private int Id;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
