 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.handle.CustomerHandle;
 import com.gnamp.server.model.Customer;
 import com.gnamp.server.query.Condition;
 import com.gnamp.server.query.CustomerColumn;
 import com.gnamp.server.query.Order;
 import com.gnamp.server.query.Result;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import java.util.TreeSet;
 import org.apache.log4j.Logger;
 
 public class CustomerAction
   extends JSONAction
 {
   private String oName;
   private String oManaer;
   PageBean page = new PageBean();
   Logger log = Logger.getLogger(CustomerAction.class);
   private static final String PASSWORD = "admin";
   Customer customer;
   List<Integer> customers;
   
   public void add()
   {
     CustomerHandle handle = new CustomerHandle(this);
     try
     {
       if (handle.create(this.customer)) {
         JsonUtils.writeSuccess(this.response);
       } else {
         JsonUtils.writeErrorMessage(this.response, getText("createclienterror"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("createclienterror"));
       this.log.error(e.getMessage());
     }
   }
   
   public Customer getCustomer()
   {
     return this.customer;
   }
   
   public List<Integer> getCustomers()
   {
     return this.customers;
   }
   
   public String getOManaer()
   {
     return this.oManaer;
   }
   
   public String getOName()
   {
     return this.oName;
   }
   
   public CustomerColumn getOrderName()
   {
     if ("SHORT_NAME".equalsIgnoreCase(getOName())) {
       return CustomerColumn.SHORT_NAME;
     }
     if ("DESCP".equalsIgnoreCase(getOName())) {
       return CustomerColumn.DESCP;
     }
     if ("DEV_COUNT".equalsIgnoreCase(getOName())) {
       return CustomerColumn.DEV_COUNT;
     }
     return CustomerColumn.NAME;
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void list()
   {
     try
     {
       CustomerHandle handle = new CustomerHandle(this);
       if (this.page.getPageSize() == -1)
       {
         List<Customer> customers = handle.readAll();
         this.page.setTotalRows(customers.size());
         this.page.setPageSize(customers.size());
         response(JSONSuccessString(JSONArrayToString(customers), new Map[] {
           new MapTool().putObject("page", this.page) }));
       }
       else
       {
         Result<Customer> results = handle.readPage(
           new Condition(
           (this.page.getCurrentPage() - 1) * this.page.getPageSize(), 
           this.page.getPageSize(), 
           getOrderName(), 
           "asc".equals(getOManaer()) ? Order.ASC : Order.DESC));
         
 
         this.page.setTotalRows(results.getTotal());
         response(JSONSuccessString(JSONArrayToString(results.getResult()), new Map[] {
           new MapTool().putObject("page", this.page) }));
       }
     }
     catch (Exception e)
     {
       this.log.error(e.getMessage());
       JsonUtils.writeErrorMessage(this.response, getText("queryerror"));
     }
   }
   
   public String preadd()
   {
     return "preadd";
   }
   
   public String preedit()
   {
     return "preedit";
   }
   
   public void remove()
   {
     Set<Integer> errors = new TreeSet();
     CustomerHandle handle = new CustomerHandle(this);
     try
     {
       for (Integer i : this.customers) {
         try
         {
           if (!handle.remove(i.intValue())) {
             errors.add(i);
           }
         }
         catch (Exception e)
         {
           errors.add(i);
         }
       }
       if (errors.size() > 0) {
         throw new Exception();
       }
       JsonUtils.writeSuccess(this.response);
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("deleteerror") + " ID:" + errors);
       this.log.error(e.getMessage());
     }
   }
   
   public void edit()
   {
     CustomerHandle handle = new CustomerHandle(this);
     try
     {
       Customer tempCustomer = handle.read(this.customer.getCstmId());
       tempCustomer.setDescription(this.customer.getDescription());
       if (handle.modify(tempCustomer)) {
         JsonUtils.writeSuccess(this.response);
       } else {
         JsonUtils.writeErrorMessage(this.response, getText("editerror"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("editerror"));
       this.log.error(e.getMessage());
     }
   }
   
   public void searchBean()
   {
     try
     {
       CustomerHandle handle = new CustomerHandle(this);
       
       JsonUtils.writeSuccessData(this.response, 
         handle.read(this.customer.getCstmId()));
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("queryerror"));
       this.log.error(e.getMessage());
     }
   }
   
   public void reset()
   {
     CustomerHandle handle = new CustomerHandle(this);
     try
     {
       if (handle.resetAdminPassword(this.customer.getCstmId(), "admin")) {
         JsonUtils.writeSuccess(this.response);
       } else {
         JsonUtils.writeErrorMessage(this.response, getText("reseterror"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("reseterror"));
       this.log.error(e.getMessage());
     }
   }
   
   public void setCustomer(Customer customer)
   {
     this.customer = customer;
   }
   
   public void setCustomers(List<Integer> customers)
   {
     this.customers = customers;
   }
   
   public void setOManaer(String oManaer)
   {
     this.oManaer = oManaer;
   }
   
   public void setOName(String oName)
   {
     this.oName = oName;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public void validateAdd()
   {
     if (this.customer == null) {
       addFieldError("customer", getText("customerisnull"));
     }
   }
   
   public void validateRemove()
   {
     if (this.customers == null) {
       addFieldError("cusotmers", "customersisnull");
     } else if (this.customers.size() == 0) {
       addFieldError("cusotmers", "cusotmerslengtherror");
     }
   }
   
   public void validateReset()
   {
     if (this.customer == null)
     {
       addFieldError("customer", getText("customerisnull"));
       return;
     }
     if (this.customer.getCstmId() < 0) {
       addFieldError("customer", getText("customeriderror"));
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.CustomerAction
 * JD-Core Version:    0.7.0.1
 */