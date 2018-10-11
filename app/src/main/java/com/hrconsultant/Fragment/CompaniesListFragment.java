package com.hrconsultant.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import com.hrconsultant.HRApp;
import com.hrconsultant.LoginActivity;
import com.hrconsultant.MainActivity;

import com.hrconsultant.adapter.FacilitiesDetailAdapter;
import com.hrconsultant.adapter.FaclitiesDetailItem;
import com.hrconsultant.request.CompanyBody;
import com.hrconsultant.request.CustomerBody;
import com.hrconsultant.request.EmployeeBody;
import com.hrconsultant.request.LoginBody;
import com.hrconsultant.response.CompanyResponse;
import com.hrconsultant.response.CustomerResponse;
import com.hrconsultant.response.EmployeeResponse;
import com.hrconsultant.response.LoginResponse;
import com.hrconsultant.utils.DialogFactory;
import com.hrconsultant.utils.NetworkFactory;
import com.hrconsultant.utils.Utils;
import com.hrconsultant.views.CustomProgressDialog;

import com.hrconsultant.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompaniesListFragment extends Fragment{
    private CustomProgressDialog customProgressDialog;
    private Spinner spCompany;
    private List<String> arrListCompany = new ArrayList<>();
    private List<CompanyResponse.DataBean> listCompany;
    private FacilitiesDetailAdapter companyAdapter;
    ArrayList<FaclitiesDetailItem> comapnyDetailList = new ArrayList<>();
    private Spinner spCustomer;
    private List<CustomerResponse.DataBean> listCustomer;
    ArrayList<FaclitiesDetailItem> customerDetailList = new ArrayList<>();
    private FacilitiesDetailAdapter customerAdapter;
    private Spinner spEmployee;
    private List<EmployeeResponse.DataBean> listEmployee;
    ArrayList<FaclitiesDetailItem> employeeDetailList = new ArrayList<>();
    private FacilitiesDetailAdapter employeeAdapter;
    private View v;
    private int comapnyId;
    private int customerId;


    public static CompaniesListFragment newInstance() {
        CompaniesListFragment fragment = new CompaniesListFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       if(v==null) {
           v = inflater.inflate(R.layout.fragment_home, container, false);
           spCompany = v.findViewById(R.id.spCompany);
           spCustomer = v.findViewById(R.id.spCustomer);
           spEmployee = v.findViewById(R.id.spEmployee);


           getCompanies();
           spCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               public void onItemSelected(AdapterView<?> parent, View view,
                                          int pos, long id) {
                   FaclitiesDetailItem mSelected = (FaclitiesDetailItem) parent.getAdapter().getItem(pos);
                    comapnyId = mSelected.getId();

                   getCustomer(comapnyId);

               }

               @Override
               public void onNothingSelected(AdapterView<?> arg0) {
                   // TODO Auto-generated method stub
               }
           });

           spCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               public void onItemSelected(AdapterView<?> parent, View view,
                                          int pos, long id) {
                   FaclitiesDetailItem mSelected = (FaclitiesDetailItem) parent.getAdapter().getItem(pos);
                    customerId = mSelected.getId();

                   getEmployee(customerId);

               }

               @Override
               public void onNothingSelected(AdapterView<?> arg0) {
                   // TODO Auto-generated method stub
               }
           });


           spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               public void onItemSelected(AdapterView<?> parent, View view,
                                          int pos, long id) {
                   FaclitiesDetailItem mSelected = (FaclitiesDetailItem) parent.getAdapter().getItem(pos);
                   int employeeid = mSelected.getId();

                   if (employeeid != -1) {
                       HomeFragment fragment = HomeFragment.newInstance(comapnyId,customerId,employeeid);
                       Utils.replaceFragmentWithoutAnimation(getFragmentManager(), fragment, HomeFragment.class.getSimpleName(), true, R.id.fragmentContainer);

                   }

               }

               @Override
               public void onNothingSelected(AdapterView<?> arg0) {
                   // TODO Auto-generated method stub
               }
           });
       }
        return v;
    }

    private void getCompanies() {

        if (NetworkFactory.getInstance().isNetworkAvailable(getActivity())) {
            customProgressDialog = new CustomProgressDialog(getActivity(), R.style.custom_progress_style, true);
            customProgressDialog.show();
            CompanyBody body = new CompanyBody();
            body.setId(HRApp.getPreferenceManager().getLoginId());

            HRApp.getInstance().getRestClient().getUserCredentialService().getCompanyData(body).enqueue(new Callback<CompanyResponse>() {
                @Override
                public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                   if(getActivity()!=null && isAdded()){
                    if(response!=null) {


                        if (response.body().getStatus() == 0) {
                            if (customProgressDialog.isShowing()) {
                                customProgressDialog.dismiss();
                            }

                            listCompany = response.body().getData();

                            arrListCompany.clear();
                            FaclitiesDetailItem item1 = new FaclitiesDetailItem();
                            item1.setId(-1);
                            item1.setFaclityName("--Select--");
                            comapnyDetailList.add(item1);
                            for (CompanyResponse.DataBean bean : listCompany) {
                                FaclitiesDetailItem item = new FaclitiesDetailItem();
                                item.setId(bean.getId());
                                item.setFaclityName(bean.getName());

                                comapnyDetailList.add(item);
                            }
                            ArrayAdapter<String> adapterVillage = new ArrayAdapter<String>(getActivity(),
                                    R.layout.simple_spinner_dropdown_item, R.id.tvFacility, arrListCompany);
                           // spCompany.setThreshold(1);//will start working from first character
                            spCompany.setAdapter(adapterVillage);


                           
                            companyAdapter = new FacilitiesDetailAdapter(getActivity(), comapnyDetailList);
                            spCompany.setAdapter(companyAdapter);
                            
                        }
                    }
                }}


                @Override
                public void onFailure(Call<CompanyResponse> call, Throwable t) {

                 if(getActivity()!=null && isAdded()) {
                     if (customProgressDialog.isShowing()) {
                         customProgressDialog.dismiss();
                         DialogFactory.getInstance().showAlertDialog(getActivity(), getString(R.string.app_name), 0, "Network error", "ok", false);
                     }

                 }

                }
            });
        } else {
            DialogFactory.getInstance().showAlertDialog(getActivity(), getString(R.string.app_name), 0, "Network error", "ok", false);
        }
    }

    private void getCustomer(int comapnyId) {

        if (NetworkFactory.getInstance().isNetworkAvailable(getActivity())) {
            customProgressDialog = new CustomProgressDialog(getActivity(), R.style.custom_progress_style, true);
            customProgressDialog.show();
            CustomerBody body = new CustomerBody();
            body.setId(comapnyId+"");

            HRApp.getInstance().getRestClient().getUserCredentialService().getCustomerData(body).enqueue(new Callback<CustomerResponse>() {
                @Override
                public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                    if(getActivity()!=null && isAdded()){
                        if(response!=null) {


                            if (response.body().getStatus() == 0) {
                                if (customProgressDialog.isShowing()) {
                                    customProgressDialog.dismiss();
                                }

                                customerDetailList.clear();
                                listCustomer = response.body().getData();


                                    FaclitiesDetailItem item1 = new FaclitiesDetailItem();
                                    item1.setId(-1);
                                    item1.setFaclityName("--Select--");
                                    customerDetailList.add(item1);



                                for (CustomerResponse.DataBean bean : listCustomer) {
                                    FaclitiesDetailItem item = new FaclitiesDetailItem();
                                    item.setId(bean.getId());
                                    item.setFaclityName(bean.getName());

                                    customerDetailList.add(item);
                                }





                                customerAdapter = new FacilitiesDetailAdapter(getActivity(), customerDetailList);
                                spCustomer.setAdapter(customerAdapter);

                            }
                        }
                    }}


                @Override
                public void onFailure(Call<CustomerResponse> call, Throwable t) {

                    if(getActivity()!=null && isAdded()) {
                        if (customProgressDialog.isShowing()) {
                            customProgressDialog.dismiss();
                            DialogFactory.getInstance().showAlertDialog(getActivity(), getString(R.string.app_name), 0, "Network error", "ok", false);
                        }

                    }

                }
            });
        } else {
            DialogFactory.getInstance().showAlertDialog(getActivity(), getString(R.string.app_name), 0, "Network error", "ok", false);
        }
    }

    private void getEmployee(int customerId) {

        if (NetworkFactory.getInstance().isNetworkAvailable(getActivity())) {
            customProgressDialog = new CustomProgressDialog(getActivity(), R.style.custom_progress_style, true);
            customProgressDialog.show();
            EmployeeBody body = new EmployeeBody();
            body.setId(customerId+"");

            HRApp.getInstance().getRestClient().getUserCredentialService().getEmployeeData(body).enqueue(new Callback<EmployeeResponse>() {
                @Override
                public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                    if(getActivity()!=null && isAdded()){
                        if(response!=null) {


                            if (response.body().getStatus() == 0) {
                                if (customProgressDialog.isShowing()) {
                                    customProgressDialog.dismiss();
                                }

                                employeeDetailList.clear();
                                listEmployee = response.body().getData();

                                    FaclitiesDetailItem item1 = new FaclitiesDetailItem();
                                    item1.setId(-1);
                                    item1.setFaclityName("--Select--");
                                    employeeDetailList.add(item1);


                                for (EmployeeResponse.DataBean bean : listEmployee) {
                                    FaclitiesDetailItem item = new FaclitiesDetailItem();
                                    item.setId(bean.getId());
                                    item.setFaclityName(bean.getName());

                                    employeeDetailList.add(item);
                                }



                                employeeAdapter = new FacilitiesDetailAdapter(getActivity(), employeeDetailList);
                                spEmployee.setAdapter(employeeAdapter);

                            }
                        }
                    }}


                @Override
                public void onFailure(Call<EmployeeResponse> call, Throwable t) {

                    if(getActivity()!=null && isAdded()) {
                        if (customProgressDialog.isShowing()) {
                            customProgressDialog.dismiss();
                            DialogFactory.getInstance().showAlertDialog(getActivity(), getString(R.string.app_name), 0, "Network error", "ok", false);
                        }

                    }

                }
            });
        } else {
            DialogFactory.getInstance().showAlertDialog(getActivity(), getString(R.string.app_name), 0, "Network error", "ok", false);
        }
    }
}
