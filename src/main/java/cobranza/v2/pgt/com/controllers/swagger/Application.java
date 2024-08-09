
package cobranza.v2.pgt.com.controllers.swagger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Application {
  
  private Long id;
  private String admin_graphql_api_id;
  private float app_id;
  private String browser_ip;
  private boolean buyer_accepts_marketing;
  private String cancel_reason = null;
  private String cancelled_at = null;
  private String cart_token = null;
  private float checkout_id;
  private String checkout_token;
  Client_details Client_details;
  private String closed_at = null;
  private boolean confirmed;
  private String contact_email;
  private String created_at;
  private String currency;
  private String current_subtotal_price;
  Current_subtotal_price_set Current_subtotal_price_set;
  private String current_total_discounts;
  Current_total_discounts_set Current_total_discounts_set;
  private String current_total_duties_set = null;
  private String current_total_price;
  Current_total_price_set Current_total_price_set;
  private String current_total_tax;
  Current_total_tax_set Current_total_tax_set;
  private String customer_locale;
  private String device_id = null;
  ArrayList<Object> discount_codes = new ArrayList<>( );
  private String email;
  private boolean estimated_taxes;
  private String financial_status;
  private String fulfillment_status = null;
  private String gateway;
  private String landing_site;
  private String landing_site_ref = null;
  private String location_id = null;
  private String name;
  private String note = null;
  ArrayList<NoteAttribute> note_attributes = new ArrayList<>( );
  private float number;
  private String order_number;
  private String order_status_url;
  private String original_total_duties_set = null;
  ArrayList<Object> payment_gateway_names = new ArrayList<>( );
  private String phone = null;
  private String presentment_currency;
  private String processed_at;
  private String processing_method;
  private String reference = null;
  private String referring_site;
  private String source_identifier = null;
  private String source_name;
  private String source_url = null;
  private String subtotal_price;
  Subtotal_price_set Subtotal_price_set;
  private String tags;
  ArrayList<Object> tax_lines = new ArrayList<>( );
  private boolean taxes_included;
  private boolean test;
  private String token;
  private String total_discounts;
  Total_discounts_set Total_discounts_set;
  private String total_line_items_price;
  Total_line_items_price_set Total_line_items_price_set;
  private String total_outstanding;
  private String total_price;
  Total_price_set Total_price_set;
  private String total_price_usd;
  Total_shipping_price_set Total_shipping_price_set;
  private String total_tax;
  Total_tax_set Total_tax_set;
  private String total_tip_received;
  private float total_weight;
  private String updated_at;
  private String user_id = null;
  Billing_address Billing_address;
  Customer customer;
  ArrayList<DiscountApplication> discount_applications = new ArrayList<>( );
  ArrayList<Object> fulfillments = new ArrayList<>( );
  public List<LineItem> line_items;
  private String payment_details = null;
  ArrayList<Object> refunds = new ArrayList<>( );
  Shipping_address Shipping_address;
  ArrayList<Object> shipping_lines = new ArrayList<>( );
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class NoteAttribute {
  
  public String name;
  public String value;
}

@Data
class DiscountApplication {
  
  public String target_type;
  public String type;
  public String value;
  public String value_type;
  public String allocation_method;
  public String target_selection;
  public String title;
  public String description;
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class OriginLocation {
  
  public Object id;
  public String country_code;
  public String province_code;
  public String name;
  public String address1;
  public String address2;
  public String city;
  public String zip;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class LineItem {
  
  public Object id;
  public String admin_graphql_api_id;
  public int fulfillable_quantity;
  public String fulfillment_service;
  public Object fulfillment_status;
  public boolean gift_card;
  public int grams;
  public String name;
  public OriginLocation origin_location;
  public String price;
  public PriceSet price_set;
  public boolean product_exists;
  public Object product_id;
  public List<Object> properties;
  public int quantity;
  public boolean requires_shipping;
  public String sku;
  public boolean taxable;
  public String title;
  public String total_discount;
  public TotalDiscountSet total_discount_set;
  public Object variant_id;
  public String variant_inventory_management;
  public String variant_title;
  public String vendor;
  public List<TaxLine> tax_lines;
  public List<Object> duties;
  public List<Object> discount_allocations;
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class TaxLine {
  
  public boolean channel_liable;
  public String price;
  public PriceSet price_set;
  public double rate;
  public String title;
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class TotalDiscountSet {
  
  public ShopMoney shop_money;
  public PresentmentMoney presentment_money;
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class PriceSet {
  
  public ShopMoney shop_money;
  public PresentmentMoney presentment_money;
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class ShopMoney {
  
  public String amount;
  public String currency_code;
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class PresentmentMoney {
  
  public String amount;
  public String currency_code;
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Shipping_address {
  
  private String first_name;
  private String address1;
  private String phone;
  private String city;
  private String zip = null;
  private String province = null;
  private String country;
  private String last_name;
  private String address2 = null;
  private String company = null;
  private float latitude;
  private float longitude;
  private String name;
  private String country_code;
  private String province_code = null;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Customer {
  
  private float id;
  private String email;
  private boolean accepts_marketing;
  private String created_at;
  private String updated_at;
  private String first_name;
  private String last_name;
  private float orders_count;
  private String state;
  private String total_spent;
  private float last_order_id;
  private String note = null;
  private boolean verified_email;
  private String multipass_identifier = null;
  private boolean tax_exempt;
  private String phone = null;
  private String tags;
  private String last_order_name;
  private String currency;
  private String accepts_marketing_updated_at;
  private String marketing_opt_in_level = null;
  public Object sms_marketing_consent;
  private String admin_graphql_api_id;
  Default_address Default_address;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Default_address {
  
  private float id;
  private float customer_id;
  private String first_name;
  private String last_name;
  private String company = null;
  private String address1;
  private String address2 = null;
  private String city;
  private String province = null;
  private String country;
  private String zip = null;
  private String phone;
  private String name;
  private String province_code = null;
  private String country_code;
  private String country_name;
  
  // private boolean default;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Billing_address {
  
  private String first_name;
  private String address1;
  private String phone;
  private String city;
  private String zip = null;
  private String province = null;
  private String country;
  private String last_name;
  private String address2 = null;
  private String company = null;
  private float latitude;
  private float longitude;
  private String name;
  private String country_code;
  private String province_code = null;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Total_tax_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Total_shipping_price_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Total_price_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Presentment_money {
  
  private String amount;
  private String currency_code;
}

// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Presentment_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Total_line_items_price_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Total_discounts_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Shop_money {
  
  private String amount;
  private String currency_code;
  
}

// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
// class Shop_money {
// private String amount;
// private String currency_code;
// String getAmount( ) { return amount; }
// String getCurrency_code( ) { return currency_code; }
// void setAmount(String amount) { this.amount = amount; }
// void setCurrency_code(String currency_code) { this.currency_code = currency_code; }
// }
@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Subtotal_price_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Current_total_tax_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Current_total_price_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Current_total_discounts_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Current_subtotal_price_set {
  
  Shop_money Shop_money;
  Presentment_money Presentment_money;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Client_details {
  
  private String accept_language;
  private float browser_height;
  private String browser_ip;
  private float browser_width;
  private String session_hash = null;
  private String user_agent;
  
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Root { public Order orders; }

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Order {
  
  public Long id;
  public long main_id_order;
  public int main_id_shop;
  public Long id_shop;
  public String total_price;
  public String payment_status;
  public String customer;
  public String gateway;
  public String order_name;
  public String fulfillment;
  public int mp_shipping;
  public String customer_email;
  public String address1;
  public String address2;
  public String company;
  public String city;
  public String zip;
  public String province;
  public String country;
  public String phone;
  public String billing_address;
  public String shipping_address;
  public int is_mail_sent;
  public String capture_payment_mail;
  public int order_placed_mail;
  public int is_deleted;
  public String order_note;
  public String delivery_method;
  public int is_nav_synced;
  public long transaction_id;
  public int order_prepare_status;
  public long main_transaction_id;
  public String ref_order_id;
  public String token;
  public String checkout_token;
  public int is_paid;
  public int tax_inclusive;
  public String tax_shipping_comm_type;
  public int is_cachable;
  public String fulfillment_service_name;
  public int discount_bear_by;
  public int commission_calculated_on;
  public String order_status_url;
  public int is_refunded;
  public int gateway_processed;
  public String presentment_currency;
  public int payment_flow;
  public int restrict_view_order;
  public String seller_global_fixed_commission;
  public String refunded_seller_global_fixed_comm;
  public String risk_recommendation;
  public String risk_response;
  public int tip_distribution;
  public String tip_amount;
  public String fixed_transaction_amount;
  public String percent_transaction_amount;
  public int transaction_fixed_charge_rule;
  public int transaction_bear_by;
  public String payment_gateway_fee;
  public int fee_bear_by;
  public int seller_earning_added;
  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  public Date date_add;
  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  public Date date_upd;
}

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class Root2 { public Order order; }