package com.hrconsultant.response;

public class CompanyResponse {


/**
* Data : [{"Id":1,"Name":"Arco"},{"Id":2,"Name":"Jack"},{"Id":3,"Name":"Max"},{"Id":4,"Name":"Jasmin"}]
* Status : 0
*/

private  int Status ;
private  java.util.List<DataBean> Data ;
public int   getStatus() {   return Status ;}
public void  setStatus( int Status) {   this.Status = Status;}
public java.util.List<DataBean>   getData() {   return Data ;}
public void  setData( java.util.List<DataBean> Data) {   this.Data = Data;}
public static class DataBean{ /**
* Id : 1
* Name : Arco
*/

private  int Id ;
private  String Name ;
public int   getId() {   return Id ;}
public void  setId( int Id) {   this.Id = Id;}
public String   getName() {   return Name ;}
public void  setName( String Name) {   this.Name = Name;}}}
