package com.hrconsultant.restservice;




import com.hrconsultant.request.CompanyBody;
import com.hrconsultant.request.CustomerBody;
import com.hrconsultant.request.EmployeeBody;
import com.hrconsultant.request.LocationBody;
import com.hrconsultant.request.LoginBody;
import com.hrconsultant.request.ReportBody;
import com.hrconsultant.response.CompanyResponse;
import com.hrconsultant.response.CustomerResponse;
import com.hrconsultant.response.EmployeeResponse;
import com.hrconsultant.response.LocationResponse;
import com.hrconsultant.response.LoginResponse;
import com.hrconsultant.response.ReportResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;



public interface UserCredentialService {

    @POST("account/login")
    Call<LoginResponse> getLoginData(@Body LoginBody body);

    @POST("account/company")
    Call<CompanyResponse> getCompanyData(@Body CompanyBody body);

    @POST("account/customer")
    Call<CustomerResponse> getCustomerData(@Body CustomerBody body);

    @POST("account/employee")
    Call<EmployeeResponse> getEmployeeData(@Body EmployeeBody body);

    @POST("field/report")
    Call<ReportResponse> saveReport(@Body ReportBody body);

    @POST("field/userlocation")
    Call<LocationResponse> saveLocation(@Body LocationBody body);

}
