
package cobranza.v2.pgt.com.models.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cybersource", schema = "pgt")
public class CyberSource {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idcyber;
  @Column(length = 3000)
  private String utf8;
  @Column(length = 3000)
  private String auth_cv_result;
  @Column(length = 3000)
  private String req_locale;
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  @Column(length = 3000)
  private String payer_authentication_cavv;
  @Column(length = 3000)
  private String score_device_fingerprint_true_ipaddress_attributes;
  @Column(length = 3000)
  private String req_cad_expiry_date;
  @Column(length = 3000)
  private String req_bill_to_addres_city;
  @Column(length = 3000)
  private String decision_case_priority;
  @Column(length = 3000)
  private String score_identity_info;
  @Column(length = 3000)
  private String auth_trans_ref_no;
  @Column(length = 3000)
  private String req_bill_to_surname;
  @Column(length = 3000)
  private String score_rcode;
  @Column(length = 3000)
  private String auth_amount;
  @Column(length = 3000)
  private String req_payer_authentication_merchant_name;
  @Column(length = 3000)
  private String auth_time;
  @Column(length = 3000)
  private String decision_early_return_code;
  @Column(length = 3000)
  private String transaction_id;
  @Column(length = 3000)
  private String score_device_fingerprint_javascript_enabled;
  @Column(length = 3000)
  private String payer_authentication_pares_status;
  @Column(length = 3000)
  private String payer_authentication_cav;
  @Column(length = 3000)
  private String auth_code;
  @Column(length = 3000)
  private String payer_authentication_specification_version;
  @Column(length = 3000)
  private String req_bill_to_address_country;
  @Column(length = 3000)
  private String auth_cv_result_raw;
  @Column(length = 3000)
  private String score_device_fingerprint_cookies_enabled;
  @Column(length = 3000)
  private String score_suspicious_info;
  @Column(length = 3000)
  private String decision_rcode;
  @Column(length = 3000)
  private String score_rmsg;
  @Column(length = 3000)
  private String score_device_fingerprint_browser_language;
  @Column(length = 3000)
  private String req_bill_to_address_line1;
  @Column(length = 3000)
  private String req_card_number;
  @Column(length = 3000)
  private String score_device_fingerprint_true_ipaddress;
  @Column(length = 3000)
  private String signature;
  @Column(length = 3000)
  private String auth_cavv_result;
  @Column(length = 3000)
  private String request_token;
  @Column(length = 3000)
  private String req_amount;
  @Column(length = 3000)
  private String payer_authentication_reason_code;
  @Column(length = 3000)
  private String decision;
  @Column(length = 3000)
  private String decision_return_code;
  @Column(length = 3000)
  private String signed_field_names;
  @Column(length = 3000)
  private String decision_reason_code;
  @Column(length = 3000)
  private String payer_authentication_eci;
  @Column(length = 3000)
  private String score_time_local;
  @Column(length = 3000)
  private String req_transaction_type;
  @Column(length = 3000)
  private String payer_authentication_xid;
  @Column(length = 3000)
  private String score_internet_info;
  @Column(length = 3000)
  private String req_reference_number;
  @Column(length = 3000)
  private String score_device_fingerprint_flash_enabled;
  @Column(length = 3000)
  private String score_device_fingerprint_images_enabled;
  @Column(length = 3000)
  private String req_payer_authentication_indicator;
  @Column(length = 3000)
  private String score_score_result;
  @Column(length = 3000)
  private String req_card_type_selection_indicator;
  @Column(length = 3000)
  private String payer_authentication_enroll_veres_enrolled;
  @Column(length = 3000)
  private String payer_authentication_proof_xml;
  @Column(length = 3000)
  private String req_card_expiry_date;
  @Column(length = 3000)
  private String score_rflag;
  @Column(length = 3000)
  private String score_card_issuer;
  @Column(length = 3000)
  private String auth_response;
  @Column(length = 3000)
  private String bill_trans_ref_no;
  @Column(length = 3000)
  private String req_payment_method;
  @Column(length = 3000)
  private String score_device_fingerprint_true_ipaddress_city;
  @Column(length = 3000)
  private String payer_authentication_enroll_e_commerce_indicator;
  @Column(length = 3000)
  private String req_card_type;
  @Column(length = 3000)
  private String payer_authentication_transaction_id;
  @Column(length = 3000)
  private String score_device_fingerprint_screen_resolution;
  @Column(length = 3000)
  private String auth_avs_code;
  @Column(length = 3000)
  private String score_address_info;
  @Column(length = 3000)
  private String score_factors;
  @Column(length = 3000)
  private String score_model_used;
  @Column(length = 3000)
  private String decision_rmsg;
  @Column(length = 3000)
  private String req_profile_id;
  @Column(length = 3000)
  private String score_device_fingerprint_hash;
  @Column(length = 3000)
  private String decision_rflag;
  @Column(length = 3000)
  private String signed_date_time;
  @Column(length = 3000)
  private String score_card_scheme;
  @Column(length = 3000)
  private String score_device_fingerprint_true_ipaddress_country;
  @Column(length = 3000)
  private String score_bin_country;
  @Column(length = 3000)
  private String req_bill_to_address_city;
  @Column(length = 3000)
  private String req_bill_to_address_postal_code;
  @Column(length = 3000)
  private String score_reason_code;
  @Column(length = 3000)
  private String reason_code;
  @Column(length = 3000)
  private String req_bill_to_forename;
  @Column(length = 3000)
  private String req_payer_authentication_acs_window_size;
  @Column(length = 3000)
  private String req_device_fingerprint_id;
  @Column(length = 3000)
  private String auth_cavv_result_raw;
  @Column(length = 3000)
  private String score_card_account_type;
  @Column(length = 3000)
  private String req_bill_to_email;
  @Column(length = 3000)
  private String auth_avs_code_raw;
  @Column(length = 3000)
  private String req_currency;
  @Column(length = 3000)
  private String score_device_fingerprint_smart_id_confidence_level;
  @Column(length = 3000)
  private String message;
  @Column(length = 3000)
  private String req_transaction_uuid;
  @Column(length = 3000)
  private String score_device_fingerprint_smart_id;
  @Column(length = 3000)
  private String score_return_code;
  @Column(length = 3000)
  private String score_host_severity;
  @Column(length = 3000)
  private String req_access_key;
  @Column(length = 3000)
  private String decision_early_reason_code;
  @Column(length = 3000)
  private String payer_authentication_validate_result;
  @Column(length = 3000)
  private String req_bill_to_address_state;
  @Column(length = 3000)
  private String decision_early_rcode;
  @Column(length = 3000)
  private String req_merchant_defined_data1;
  @Column(length = 3000)
  private String req_merchant_defined_data2;
  @Column(length = 3000)
  private String req_merchant_defined_data3;
  @Column(length = 3000)
  private String req_merchant_defined_data4;
  @Column(length = 3000)
  private String req_merchant_defined_data5;
  @Column(length = 3000)
  private String req_merchant_defined_data6;
  @Column(length = 3000)
  private String req_merchant_defined_data7;
  @Column(length = 3000)
  private String req_merchant_defined_data8;
  @Column(length = 3000)
  private String req_merchant_defined_data9;
  @Column(length = 3000)
  private String req_merchant_defined_data10;
  @Column(length = 3000)
  private String req_merchant_defined_data11;
  @Column(length = 3000)
  private String req_merchant_defined_data12;
  @Column(length = 3000)
  private String req_merchant_defined_data13;
  @Column(length = 3000)
  private String req_merchant_defined_data14;
  @Column(length = 3000)
  private String req_merchant_defined_data15;
  @Column(length = 3000)
  private String req_merchant_defined_data16;
  @Column(length = 3000)
  private String req_merchant_defined_data17;
  @Column(length = 3000)
  private String req_merchant_defined_data18;
  @Column(length = 3000)
  private String req_merchant_defined_data19;
  @Column(length = 3000)
  private String req_merchant_defined_data20;
  @Column(length = 3000)
  private String req_merchant_defined_data21;
  @Column(length = 3000)
  private String req_merchant_defined_data22;
  @Column(length = 3000)
  private String req_merchant_defined_data23;
  @Column(length = 3000)
  private String req_merchant_defined_data24;
  @Column(length = 3000)
  private String req_merchant_defined_data25;
  @Column(length = 3000)
  private String req_merchant_defined_data26;
  @Column(length = 3000)
  private String req_merchant_defined_data27;
  @Column(length = 3000)
  private String req_merchant_defined_data28;
  @Column(length = 3000)
  private String req_merchant_defined_data29;
  @Column(length = 3000)
  private String req_merchant_defined_data30;
  @Column(length = 3000)
  private String req_merchant_defined_data31;
  @Column(length = 3000)
  private String req_merchant_defined_data32;
  @Column(length = 3000)
  private String req_merchant_defined_data33;
  @Column(length = 3000)
  private String req_merchant_defined_data34;
  @Column(length = 3000)
  private String req_merchant_defined_data35;
  @Column(length = 3000)
  private String req_merchant_defined_data36;
  @Column(length = 3000)
  private String req_merchant_defined_data37;
  @Column(length = 3000)
  private String req_merchant_defined_data38;
  @Column(length = 3000)
  private String req_merchant_defined_data39;
  @Column(length = 3000)
  private String req_merchant_defined_data40;
  @Column(length = 3000)
  private String req_merchant_defined_data41;
  @Column(length = 3000)
  private String req_merchant_defined_data42;
  @Column(length = 3000)
  private String req_merchant_defined_data43;
  @Column(length = 3000)
  private String req_merchant_defined_data44;
  @Column(length = 3000)
  private String req_merchant_defined_data45;
  @Column(length = 3000)
  private String req_merchant_defined_data46;
  @Column(length = 3000)
  private String req_merchant_defined_data47;
  @Column(length = 3000)
  private String req_merchant_defined_data48;
  @Column(length = 3000)
  private String req_merchant_defined_data49;
  @Column(length = 3000)
  private String req_merchant_defined_data50;
  @Column(length = 3000)
  private String score_velocity_info;
  @Column(length = 3000)
  private String req_merchant_defined_data51;
  @Column(length = 3000)
  private String req_merchant_defined_data52;
  @Column(length = 3000)
  private String req_merchant_defined_data53;
  @Column(length = 3000)
  private String req_merchant_defined_data54;
  @Column(length = 3000)
  private String req_merchant_defined_data55;
  @Column(length = 3000)
  private String req_merchant_defined_data56;
  @Column(length = 3000)
  private String req_merchant_defined_data57;
  @Column(length = 3000)
  private String decision_velocity_info;
  @Column(length = 3000)
  private String payer_authentication_validate_e_commerce_indicator;
  @Column(length = 3000)
  private String req_merchant_defined_data58;
  @Column(length = 3000)
  private String req_merchant_defined_data59;
  @Column(length = 3000)
  private String req_merchant_defined_data60;
  @Column(length = 3000)
  private String req_merchant_defined_data61;
  @Column(length = 3000)
  private String req_merchant_defined_data62;
  @Column(length = 3000)
  private String req_merchant_defined_data63;
  @Column(length = 3000)
  private String req_merchant_defined_data64;
  @Column(length = 3000)
  private String req_merchant_defined_data65;
  @Column(length = 3000)
  private String req_merchant_defined_data66;
  @Column(length = 3000)
  private String req_merchant_defined_data67;
  @Column(length = 3000)
  private String req_merchant_defined_data68;
  @Column(length = 3000)
  private String req_merchant_defined_data69;
  @Column(length = 3000)
  private String req_merchant_defined_data70;
  @Column(length = 3000)
  private String req_merchant_defined_data71;
  @Column(length = 3000)
  private String req_merchant_defined_data72;
  @Column(length = 3000)
  private String req_merchant_defined_data73;
  @Column(length = 3000)
  private String req_merchant_defined_data74;
  @Column(length = 3000)
  private String req_merchant_defined_data75;
  @Column(length = 3000)
  private String req_merchant_defined_data76;
  @Column(length = 3000)
  private String req_merchant_defined_data77;
  @Column(length = 3000)
  private String req_merchant_defined_data78;
  @Column(length = 3000)
  private String req_merchant_defined_data79;
  @Column(length = 3000)
  private String req_merchant_defined_data80;
  @Column(length = 3000)
  private String req_merchant_defined_data81;
  @Column(length = 3000)
  private String req_merchant_defined_data82;
  @Column(length = 3000)
  private String req_merchant_defined_data83;
  @Column(length = 3000)
  private String req_merchant_defined_data84;
  @Column(length = 3000)
  private String req_merchant_defined_data85;
  @Column(length = 3000)
  private String req_merchant_defined_data86;
  @Column(length = 3000)
  private String req_merchant_defined_data87;
  @Column(length = 3000)
  private String req_merchant_defined_data88;
  @Column(length = 3000)
  private String req_merchant_defined_data89;
  @Column(length = 3000)
  private String req_merchant_defined_data90;
  @Column(length = 3000)
  private String req_merchant_defined_data91;
  @Column(length = 3000)
  private String req_merchant_defined_data92;
  @Column(length = 3000)
  private String req_merchant_defined_data93;
  @Column(length = 3000)
  private String req_merchant_defined_data94;
  @Column(length = 3000)
  private String req_merchant_defined_data95;
  @Column(length = 3000)
  private String req_merchant_defined_data96;
  @Column(length = 3000)
  private String req_merchant_defined_data97;
  @Column(length = 3000)
  private String req_merchant_defined_data98;
  @Column(length = 3000)
  private String req_merchant_defined_data99;
  @Column(length = 3000)
  private String req_merchant_defined_data100;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "idrecibo")
  @JsonIgnoreProperties({"idcyber","hibernateLazyInitializer","handler"})
  private Recibo idrecibo;
  @JsonIgnoreProperties({"idcyber","idrecibo","hibernateLazyInitializer","handler"})
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idparametrica")
  private Parametrica idparametrica;
  @Column(length = 3000)
  private String card_type_name;
  @Column(length = 3000)
  private String invalid_fields;
  @Column(length = 3000)
  private String payer_authentication_uci;
  
  public Recibo getIdrecibo() { return this.idrecibo; }
  public void setIdrecibo(Recibo idrecibo) { this.idrecibo = idrecibo; }

  public Parametrica getIdparametrica( ) { return idparametrica; }
  
  public String getCard_type_name( ) { return card_type_name; }
  
  public void setCard_type_name(String card_type_name) { this.card_type_name = card_type_name; }
  
  public String getPayer_authentication_uci( ) { return payer_authentication_uci; }
  
  public void setPayer_authentication_uci(String payer_authentication_uci) {
    this.payer_authentication_uci = payer_authentication_uci;
  }
  
  public String getInvalid_fields( ) { return invalid_fields; }
  
  public void setInvalid_fields(String invalid_fields) { this.invalid_fields = invalid_fields; }
  
  public void setIdparametrica(Parametrica idparametrica) { this.idparametrica = idparametrica; }
  
  public String getUtf8( ) { return utf8; }
  
  public void setUtf8(String utf8) { this.utf8 = utf8; }
  
  public String getAuth_cv_result( ) { return auth_cv_result; }
  
  public void setAuth_cv_result(String auth_cv_result) { this.auth_cv_result = auth_cv_result; }
  
  public String getReq_locale( ) { return req_locale; }
  
  public void setReq_locale(String req_locale) { this.req_locale = req_locale; }
  
  public String getDecision_case_priority( ) { return decision_case_priority; }
  
  public void setDecision_case_priority(String decision_case_priority) {
    this.decision_case_priority = decision_case_priority;
  }
  
  public String getScore_device_fingerprint_true_ipaddress_attributes( ) {
    return score_device_fingerprint_true_ipaddress_attributes;
  }
  
  public
         void
         setScore_device_fingerprint_true_ipaddress_attributes(String score_device_fingerprint_true_ipaddress_attributes)
  {
    this.score_device_fingerprint_true_ipaddress_attributes = score_device_fingerprint_true_ipaddress_attributes;
  }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getReq_cad_expiry_date( ) { return req_cad_expiry_date; }
  
  public void setReq_cad_expiry_date(String req_cad_expiry_date) {
    this.req_cad_expiry_date = req_cad_expiry_date;
  }
  
  public String getReq_bill_to_addres_city( ) { return req_bill_to_addres_city; }
  
  public void setReq_bill_to_addres_city(String req_bill_to_addres_city) {
    this.req_bill_to_addres_city = req_bill_to_addres_city;
  }
  
  public String getAuth_trans_ref_no( ) { return auth_trans_ref_no; }
  
  public void setAuth_trans_ref_no(String auth_trans_ref_no) { this.auth_trans_ref_no = auth_trans_ref_no; }
  
  public String getPayer_authentication_cavv( ) { return payer_authentication_cavv; }
  
  public void setPayer_authentication_cavv(String payer_authentication_cavv) {
    this.payer_authentication_cavv = payer_authentication_cavv;
  }
  
  public String getReq_bill_to_surname( ) { return req_bill_to_surname; }
  
  public void setReq_bill_to_surname(String req_bill_to_surname) {
    this.req_bill_to_surname = req_bill_to_surname;
  }
  
  public String getScore_rcode( ) { return score_rcode; }
  
  public void setScore_rcode(String score_rcode) { this.score_rcode = score_rcode; }
  
  public String getScore_identity_info( ) { return score_identity_info; }
  
  public void setScore_identity_info(String score_identity_info) {
    this.score_identity_info = score_identity_info;
  }
  
  public String getReq_merchant_defined_data6( ) { return req_merchant_defined_data6; }
  
  public void setReq_merchant_defined_data6(String req_merchant_defined_data6) {
    this.req_merchant_defined_data6 = req_merchant_defined_data6;
  }
  
  public String getDecision_velocity_info( ) { return decision_velocity_info; }
  
  public void setDecision_velocity_info(String decision_velocity_info) {
    this.decision_velocity_info = decision_velocity_info;
  }
  
  public String getPayer_authentication_validate_e_commerce_indicator( ) {
    return payer_authentication_validate_e_commerce_indicator;
  }
  
  public
         void
         setPayer_authentication_validate_e_commerce_indicator(String payer_authentication_validate_e_commerce_indicator)
  {
    this.payer_authentication_validate_e_commerce_indicator = payer_authentication_validate_e_commerce_indicator;
  }
  
  public String getAuth_amount( ) { return auth_amount; }
  
  public void setAuth_amount(String auth_amount) { this.auth_amount = auth_amount; }
  
  public String getReq_payer_authentication_merchant_name( ) {
    return req_payer_authentication_merchant_name;
  }
  
  public void setReq_payer_authentication_merchant_name(String req_payer_authentication_merchant_name) {
    this.req_payer_authentication_merchant_name = req_payer_authentication_merchant_name;
  }
  
  public String getAuth_time( ) { return auth_time; }
  
  public void setAuth_time(String auth_time) { this.auth_time = auth_time; }
  
  public String getDecision_early_return_code( ) { return decision_early_return_code; }
  
  public void setDecision_early_return_code(String decision_early_return_code) {
    this.decision_early_return_code = decision_early_return_code;
  }
  
  public String getTransaction_id( ) { return transaction_id; }
  
  public void setTransaction_id(String transaction_id) { this.transaction_id = transaction_id; }
  
  public String getScore_device_fingerprint_javascript_enabled( ) {
    return score_device_fingerprint_javascript_enabled;
  }
  
  public
         void
         setScore_device_fingerprint_javascript_enabled(String score_device_fingerprint_javascript_enabled)
  {
    this.score_device_fingerprint_javascript_enabled = score_device_fingerprint_javascript_enabled;
  }
  
  public String getPayer_authentication_pares_status( ) { return payer_authentication_pares_status; }
  
  public void setPayer_authentication_pares_status(String payer_authentication_pares_status) {
    this.payer_authentication_pares_status = payer_authentication_pares_status;
  }
  
  public String getPayer_authentication_cav( ) { return payer_authentication_cav; }
  
  public void setPayer_authentication_cav(String payer_authentication_cav) {
    this.payer_authentication_cav = payer_authentication_cav;
  }
  
  public String getAuth_code( ) { return auth_code; }
  
  public void setAuth_code(String auth_code) { this.auth_code = auth_code; }
  
  public String getPayer_authentication_specification_version( ) {
    return payer_authentication_specification_version;
  }
  
  public void setPayer_authentication_specification_version(
                                                            String payer_authentication_specification_version)
  {
    this.payer_authentication_specification_version = payer_authentication_specification_version;
  }
  
  public String getReq_bill_to_address_country( ) { return req_bill_to_address_country; }
  
  public void setReq_bill_to_address_country(String req_bill_to_address_country) {
    this.req_bill_to_address_country = req_bill_to_address_country;
  }
  
  public String getAuth_cv_result_raw( ) { return auth_cv_result_raw; }
  
  public void setAuth_cv_result_raw(String auth_cv_result_raw) {
    this.auth_cv_result_raw = auth_cv_result_raw;
  }
  
  public String getScore_device_fingerprint_cookies_enabled( ) {
    return score_device_fingerprint_cookies_enabled;
  }
  
  public void setScore_device_fingerprint_cookies_enabled(String score_device_fingerprint_cookies_enabled) {
    this.score_device_fingerprint_cookies_enabled = score_device_fingerprint_cookies_enabled;
  }
  
  public String getScore_suspicious_info( ) { return score_suspicious_info; }
  
  public void setScore_suspicious_info(String score_suspicious_info) {
    this.score_suspicious_info = score_suspicious_info;
  }
  
  public String getDecision_rcode( ) { return decision_rcode; }
  
  public void setDecision_rcode(String decision_rcode) { this.decision_rcode = decision_rcode; }
  
  public String getScore_rmsg( ) { return score_rmsg; }
  
  public void setScore_rmsg(String score_rmsg) { this.score_rmsg = score_rmsg; }
  
  public String getScore_velocity_info( ) { return score_velocity_info; }
  
  public void setScore_velocity_info(String score_velocity_info) {
    this.score_velocity_info = score_velocity_info;
  }
  
  public String getScore_device_fingerprint_browser_language( ) {
    return score_device_fingerprint_browser_language;
  }
  
  public void setScore_device_fingerprint_browser_language(String score_device_fingerprint_browser_language) {
    this.score_device_fingerprint_browser_language = score_device_fingerprint_browser_language;
  }
  
  public String getReq_bill_to_address_line1( ) { return req_bill_to_address_line1; }
  
  public void setReq_bill_to_address_line1(String req_bill_to_address_line1) {
    this.req_bill_to_address_line1 = req_bill_to_address_line1;
  }
  
  public String getReq_card_number( ) { return req_card_number; }
  
  public void setReq_card_number(String req_card_number) { this.req_card_number = req_card_number; }
  
  public String getScore_device_fingerprint_true_ipaddress( ) {
    return score_device_fingerprint_true_ipaddress;
  }
  
  public void setScore_device_fingerprint_true_ipaddress(String score_device_fingerprint_true_ipaddress) {
    this.score_device_fingerprint_true_ipaddress = score_device_fingerprint_true_ipaddress;
  }
  
  public String getSignature( ) { return signature; }
  
  public void setSignature(String signature) { this.signature = signature; }
  
  public String getAuth_cavv_result( ) { return auth_cavv_result; }
  
  public void setAuth_cavv_result(String auth_cavv_result) { this.auth_cavv_result = auth_cavv_result; }
  
  public String getRequest_token( ) { return request_token; }
  
  public void setRequest_token(String request_token) { this.request_token = request_token; }
  
  public String getReq_amount( ) { return req_amount; }
  
  public void setReq_amount(String req_amount) { this.req_amount = req_amount; }
  
  public String getPayer_authentication_reason_code( ) { return payer_authentication_reason_code; }
  
  public void setPayer_authentication_reason_code(String payer_authentication_reason_code) {
    this.payer_authentication_reason_code = payer_authentication_reason_code;
  }
  
  public String getDecision( ) { return decision; }
  
  public void setDecision(String decision) { this.decision = decision; }
  
  public String getDecision_return_code( ) { return decision_return_code; }
  
  public void setDecision_return_code(String decision_return_code) {
    this.decision_return_code = decision_return_code;
  }
  
  public String getSigned_field_names( ) { return signed_field_names; }
  
  public void setSigned_field_names(String signed_field_names) {
    this.signed_field_names = signed_field_names;
  }
  
  public String getDecision_reason_code( ) { return decision_reason_code; }
  
  public void setDecision_reason_code(String decision_reason_code) {
    this.decision_reason_code = decision_reason_code;
  }
  
  public String getPayer_authentication_eci( ) { return payer_authentication_eci; }
  
  public void setPayer_authentication_eci(String payer_authentication_eci) {
    this.payer_authentication_eci = payer_authentication_eci;
  }
  
  public String getScore_time_local( ) { return score_time_local; }
  
  public void setScore_time_local(String score_time_local) { this.score_time_local = score_time_local; }
  
  public String getReq_transaction_type( ) { return req_transaction_type; }
  
  public void setReq_transaction_type(String req_transaction_type) {
    this.req_transaction_type = req_transaction_type;
  }
  
  public String getPayer_authentication_xid( ) { return payer_authentication_xid; }
  
  public void setPayer_authentication_xid(String payer_authentication_xid) {
    this.payer_authentication_xid = payer_authentication_xid;
  }
  
  public String getScore_internet_info( ) { return score_internet_info; }
  
  public void setScore_internet_info(String score_internet_info) {
    this.score_internet_info = score_internet_info;
  }
  
  public String getReq_reference_number( ) { return req_reference_number; }
  
  public void setReq_reference_number(String req_reference_number) {
    this.req_reference_number = req_reference_number;
  }
  
  public String getScore_device_fingerprint_flash_enabled( ) {
    return score_device_fingerprint_flash_enabled;
  }
  
  public void setScore_device_fingerprint_flash_enabled(String score_device_fingerprint_flash_enabled) {
    this.score_device_fingerprint_flash_enabled = score_device_fingerprint_flash_enabled;
  }
  
  public String getScore_device_fingerprint_images_enabled( ) {
    return score_device_fingerprint_images_enabled;
  }
  
  public void setScore_device_fingerprint_images_enabled(String score_device_fingerprint_images_enabled) {
    this.score_device_fingerprint_images_enabled = score_device_fingerprint_images_enabled;
  }
  
  public String getReq_payer_authentication_indicator( ) { return req_payer_authentication_indicator; }
  
  public void setReq_payer_authentication_indicator(String req_payer_authentication_indicator) {
    this.req_payer_authentication_indicator = req_payer_authentication_indicator;
  }
  
  public String getScore_score_result( ) { return score_score_result; }
  
  public void setScore_score_result(String score_score_result) {
    this.score_score_result = score_score_result;
  }
  
  public String getReq_card_type_selection_indicator( ) { return req_card_type_selection_indicator; }
  
  public void setReq_card_type_selection_indicator(String req_card_type_selection_indicator) {
    this.req_card_type_selection_indicator = req_card_type_selection_indicator;
  }
  
  public String getPayer_authentication_enroll_veres_enrolled( ) {
    return payer_authentication_enroll_veres_enrolled;
  }
  
  public void setPayer_authentication_enroll_veres_enrolled(
                                                            String payer_authentication_enroll_veres_enrolled)
  {
    this.payer_authentication_enroll_veres_enrolled = payer_authentication_enroll_veres_enrolled;
  }
  
  public String getPayer_authentication_proof_xml( ) { return payer_authentication_proof_xml; }
  
  public void setPayer_authentication_proof_xml(String payer_authentication_proof_xml) {
    this.payer_authentication_proof_xml = payer_authentication_proof_xml;
  }
  
  public String getReq_card_expiry_date( ) { return req_card_expiry_date; }
  
  public void setReq_card_expiry_date(String req_card_expiry_date) {
    this.req_card_expiry_date = req_card_expiry_date;
  }
  
  public String getScore_rflag( ) { return score_rflag; }
  
  public void setScore_rflag(String score_rflag) { this.score_rflag = score_rflag; }
  
  public String getScore_card_issuer( ) { return score_card_issuer; }
  
  public void setScore_card_issuer(String score_card_issuer) { this.score_card_issuer = score_card_issuer; }
  
  public String getAuth_response( ) { return auth_response; }
  
  public void setAuth_response(String auth_response) { this.auth_response = auth_response; }
  
  public String getBill_trans_ref_no( ) { return bill_trans_ref_no; }
  
  public void setBill_trans_ref_no(String bill_trans_ref_no) { this.bill_trans_ref_no = bill_trans_ref_no; }
  
  public String getReq_payment_method( ) { return req_payment_method; }
  
  public void setReq_payment_method(String req_payment_method) {
    this.req_payment_method = req_payment_method;
  }
  
  public String getScore_device_fingerprint_true_ipaddress_city( ) {
    return score_device_fingerprint_true_ipaddress_city;
  }
  
  public
         void
         setScore_device_fingerprint_true_ipaddress_city(String score_device_fingerprint_true_ipaddress_city)
  {
    this.score_device_fingerprint_true_ipaddress_city = score_device_fingerprint_true_ipaddress_city;
  }
  
  public String getPayer_authentication_enroll_e_commerce_indicator( ) {
    return payer_authentication_enroll_e_commerce_indicator;
  }
  
  public
         void
         setPayer_authentication_enroll_e_commerce_indicator(String payer_authentication_enroll_e_commerce_indicator)
  {
    this.payer_authentication_enroll_e_commerce_indicator = payer_authentication_enroll_e_commerce_indicator;
  }
  
  public String getReq_card_type( ) { return req_card_type; }
  
  public void setReq_card_type(String req_card_type) { this.req_card_type = req_card_type; }
  
  public String getPayer_authentication_transaction_id( ) { return payer_authentication_transaction_id; }
  
  public void setPayer_authentication_transaction_id(String payer_authentication_transaction_id) {
    this.payer_authentication_transaction_id = payer_authentication_transaction_id;
  }
  
  public String getScore_device_fingerprint_screen_resolution( ) {
    return score_device_fingerprint_screen_resolution;
  }
  
  public void setScore_device_fingerprint_screen_resolution(
                                                            String score_device_fingerprint_screen_resolution)
  {
    this.score_device_fingerprint_screen_resolution = score_device_fingerprint_screen_resolution;
  }
  
  public String getAuth_avs_code( ) { return auth_avs_code; }
  
  public void setAuth_avs_code(String auth_avs_code) { this.auth_avs_code = auth_avs_code; }
  
  public String getScore_address_info( ) { return score_address_info; }
  
  public void setScore_address_info(String score_address_info) {
    this.score_address_info = score_address_info;
  }
  
  public String getScore_factors( ) { return score_factors; }
  
  public void setScore_factors(String score_factors) { this.score_factors = score_factors; }
  
  public String getScore_model_used( ) { return score_model_used; }
  
  public void setScore_model_used(String score_model_used) { this.score_model_used = score_model_used; }
  
  public String getDecision_rmsg( ) { return decision_rmsg; }
  
  public void setDecision_rmsg(String decision_rmsg) { this.decision_rmsg = decision_rmsg; }
  
  public String getReq_profile_id( ) { return req_profile_id; }
  
  public void setReq_profile_id(String req_profile_id) { this.req_profile_id = req_profile_id; }
  
  public String getScore_device_fingerprint_hash( ) { return score_device_fingerprint_hash; }
  
  public void setScore_device_fingerprint_hash(String score_device_fingerprint_hash) {
    this.score_device_fingerprint_hash = score_device_fingerprint_hash;
  }
  
  public String getDecision_rflag( ) { return decision_rflag; }
  
  public void setDecision_rflag(String decision_rflag) { this.decision_rflag = decision_rflag; }
  
  public String getSigned_date_time( ) { return signed_date_time; }
  
  public void setSigned_date_time(String signed_date_time) { this.signed_date_time = signed_date_time; }
  
  public String getScore_card_scheme( ) { return score_card_scheme; }
  
  public void setScore_card_scheme(String score_card_scheme) { this.score_card_scheme = score_card_scheme; }
  
  public String getScore_device_fingerprint_true_ipaddress_country( ) {
    return score_device_fingerprint_true_ipaddress_country;
  }
  
  public
         void
         setScore_device_fingerprint_true_ipaddress_country(String score_device_fingerprint_true_ipaddress_country)
  {
    this.score_device_fingerprint_true_ipaddress_country = score_device_fingerprint_true_ipaddress_country;
  }
  
  public String getScore_bin_country( ) { return score_bin_country; }
  
  public void setScore_bin_country(String score_bin_country) { this.score_bin_country = score_bin_country; }
  
  public String getReq_bill_to_address_city( ) { return req_bill_to_address_city; }
  
  public void setReq_bill_to_address_city(String req_bill_to_address_city) {
    this.req_bill_to_address_city = req_bill_to_address_city;
  }
  
  public String getReq_bill_to_address_postal_code( ) { return req_bill_to_address_postal_code; }
  
  public void setReq_bill_to_address_postal_code(String req_bill_to_address_postal_code) {
    this.req_bill_to_address_postal_code = req_bill_to_address_postal_code;
  }
  
  public String getScore_reason_code( ) { return score_reason_code; }
  
  public void setScore_reason_code(String score_reason_code) { this.score_reason_code = score_reason_code; }
  
  public String getReason_code( ) { return reason_code; }
  
  public void setReason_code(String reason_code) { this.reason_code = reason_code; }
  
  public String getReq_bill_to_forename( ) { return req_bill_to_forename; }
  
  public void setReq_bill_to_forename(String req_bill_to_forename) {
    this.req_bill_to_forename = req_bill_to_forename;
  }
  
  public String getReq_payer_authentication_acs_window_size( ) {
    return req_payer_authentication_acs_window_size;
  }
  
  public void setReq_payer_authentication_acs_window_size(String req_payer_authentication_acs_window_size) {
    this.req_payer_authentication_acs_window_size = req_payer_authentication_acs_window_size;
  }
  
  public String getReq_device_fingerprint_id( ) { return req_device_fingerprint_id; }
  
  public void setReq_device_fingerprint_id(String req_device_fingerprint_id) {
    this.req_device_fingerprint_id = req_device_fingerprint_id;
  }
  
  public String getAuth_cavv_result_raw( ) { return auth_cavv_result_raw; }
  
  public void setAuth_cavv_result_raw(String auth_cavv_result_raw) {
    this.auth_cavv_result_raw = auth_cavv_result_raw;
  }
  
  public String getScore_card_account_type( ) { return score_card_account_type; }
  
  public void setScore_card_account_type(String score_card_account_type) {
    this.score_card_account_type = score_card_account_type;
  }
  
  public String getReq_bill_to_email( ) { return req_bill_to_email; }
  
  public void setReq_bill_to_email(String req_bill_to_email) { this.req_bill_to_email = req_bill_to_email; }
  
  public String getAuth_avs_code_raw( ) { return auth_avs_code_raw; }
  
  public void setAuth_avs_code_raw(String auth_avs_code_raw) { this.auth_avs_code_raw = auth_avs_code_raw; }
  
  public String getReq_currency( ) { return req_currency; }
  
  public void setReq_currency(String req_currency) { this.req_currency = req_currency; }
  
  public String getScore_device_fingerprint_smart_id_confidence_level( ) {
    return score_device_fingerprint_smart_id_confidence_level;
  }
  
  public
         void
         setScore_device_fingerprint_smart_id_confidence_level(String score_device_fingerprint_smart_id_confidence_level)
  {
    this.score_device_fingerprint_smart_id_confidence_level = score_device_fingerprint_smart_id_confidence_level;
  }
  
  public String getMessage( ) { return message; }
  
  public void setMessage(String message) { this.message = message; }
  
  public String getReq_transaction_uuid( ) { return req_transaction_uuid; }
  
  public void setReq_transaction_uuid(String req_transaction_uuid) {
    this.req_transaction_uuid = req_transaction_uuid;
  }
  
  public String getScore_device_fingerprint_smart_id( ) { return score_device_fingerprint_smart_id; }
  
  public void setScore_device_fingerprint_smart_id(String score_device_fingerprint_smart_id) {
    this.score_device_fingerprint_smart_id = score_device_fingerprint_smart_id;
  }
  
  public String getScore_return_code( ) { return score_return_code; }
  
  public void setScore_return_code(String score_return_code) { this.score_return_code = score_return_code; }
  
  public String getScore_host_severity( ) { return score_host_severity; }
  
  public void setScore_host_severity(String score_host_severity) {
    this.score_host_severity = score_host_severity;
  }
  
  public String getReq_access_key( ) { return req_access_key; }
  
  public void setReq_access_key(String req_access_key) { this.req_access_key = req_access_key; }
  
  public String getDecision_early_reason_code( ) { return decision_early_reason_code; }
  
  public void setDecision_early_reason_code(String decision_early_reason_code) {
    this.decision_early_reason_code = decision_early_reason_code;
  }
  
  public String getPayer_authentication_validate_result( ) { return payer_authentication_validate_result; }
  
  public void setPayer_authentication_validate_result(String payer_authentication_validate_result) {
    this.payer_authentication_validate_result = payer_authentication_validate_result;
  }
  
  public String getReq_bill_to_address_state( ) { return req_bill_to_address_state; }
  
  public void setReq_bill_to_address_state(String req_bill_to_address_state) {
    this.req_bill_to_address_state = req_bill_to_address_state;
  }
  
  public String getDecision_early_rcode( ) { return decision_early_rcode; }
  
  public void setDecision_early_rcode(String decision_early_rcode) {
    this.decision_early_rcode = decision_early_rcode;
  }
  
  public Long getIdcyber( ) { return idcyber; }
  
  public void setIdcyber(Long idcyber) { this.idcyber = idcyber; }
  
  public String getReq_merchant_defined_data1( ) { return req_merchant_defined_data1; }
  
  public void setReq_merchant_defined_data1(String req_merchant_defined_data1) {
    this.req_merchant_defined_data1 = req_merchant_defined_data1;
  }
  
  public String getReq_merchant_defined_data2( ) { return req_merchant_defined_data2; }
  
  public void setReq_merchant_defined_data2(String req_merchant_defined_data2) {
    this.req_merchant_defined_data2 = req_merchant_defined_data2;
  }
  
  public String getReq_merchant_defined_data3( ) { return req_merchant_defined_data3; }
  
  public void setReq_merchant_defined_data3(String req_merchant_defined_data3) {
    this.req_merchant_defined_data3 = req_merchant_defined_data3;
  }
  
  public String getReq_merchant_defined_data4( ) { return req_merchant_defined_data4; }
  
  public void setReq_merchant_defined_data4(String req_merchant_defined_data4) {
    this.req_merchant_defined_data4 = req_merchant_defined_data4;
  }
  
  public String getReq_merchant_defined_data5( ) { return req_merchant_defined_data5; }
  
  public void setReq_merchant_defined_data5(String req_merchant_defined_data5) {
    this.req_merchant_defined_data5 = req_merchant_defined_data5;
  }
  
  public String getReq_merchant_defined_data7( ) { return req_merchant_defined_data7; }
  
  public void setReq_merchant_defined_data7(String req_merchant_defined_data7) {
    this.req_merchant_defined_data7 = req_merchant_defined_data7;
  }
  
  public String getReq_merchant_defined_data8( ) { return req_merchant_defined_data8; }
  
  public void setReq_merchant_defined_data8(String req_merchant_defined_data8) {
    this.req_merchant_defined_data8 = req_merchant_defined_data8;
  }
  
  public String getReq_merchant_defined_data9( ) { return req_merchant_defined_data9; }
  
  public void setReq_merchant_defined_data9(String req_merchant_defined_data9) {
    this.req_merchant_defined_data9 = req_merchant_defined_data9;
  }
  
  public String getReq_merchant_defined_data10( ) { return req_merchant_defined_data10; }
  
  public void setReq_merchant_defined_data10(String req_merchant_defined_data10) {
    this.req_merchant_defined_data10 = req_merchant_defined_data10;
  }
  
  public String getReq_merchant_defined_data11( ) { return req_merchant_defined_data11; }
  
  public void setReq_merchant_defined_data11(String req_merchant_defined_data11) {
    this.req_merchant_defined_data11 = req_merchant_defined_data11;
  }
  
  public String getReq_merchant_defined_data12( ) { return req_merchant_defined_data12; }
  
  public void setReq_merchant_defined_data12(String req_merchant_defined_data12) {
    this.req_merchant_defined_data12 = req_merchant_defined_data12;
  }
  
  public String getReq_merchant_defined_data13( ) { return req_merchant_defined_data13; }
  
  public void setReq_merchant_defined_data13(String req_merchant_defined_data13) {
    this.req_merchant_defined_data13 = req_merchant_defined_data13;
  }
  
  public String getReq_merchant_defined_data14( ) { return req_merchant_defined_data14; }
  
  public void setReq_merchant_defined_data14(String req_merchant_defined_data14) {
    this.req_merchant_defined_data14 = req_merchant_defined_data14;
  }
  
  public String getReq_merchant_defined_data15( ) { return req_merchant_defined_data15; }
  
  public void setReq_merchant_defined_data15(String req_merchant_defined_data15) {
    this.req_merchant_defined_data15 = req_merchant_defined_data15;
  }
  
  public String getReq_merchant_defined_data16( ) { return req_merchant_defined_data16; }
  
  public void setReq_merchant_defined_data16(String req_merchant_defined_data16) {
    this.req_merchant_defined_data16 = req_merchant_defined_data16;
  }
  
  public String getReq_merchant_defined_data17( ) { return req_merchant_defined_data17; }
  
  public void setReq_merchant_defined_data17(String req_merchant_defined_data17) {
    this.req_merchant_defined_data17 = req_merchant_defined_data17;
  }
  
  public String getReq_merchant_defined_data18( ) { return req_merchant_defined_data18; }
  
  public void setReq_merchant_defined_data18(String req_merchant_defined_data18) {
    this.req_merchant_defined_data18 = req_merchant_defined_data18;
  }
  
  public String getReq_merchant_defined_data19( ) { return req_merchant_defined_data19; }
  
  public void setReq_merchant_defined_data19(String req_merchant_defined_data19) {
    this.req_merchant_defined_data19 = req_merchant_defined_data19;
  }
  
  public String getReq_merchant_defined_data20( ) { return req_merchant_defined_data20; }
  
  public void setReq_merchant_defined_data20(String req_merchant_defined_data20) {
    this.req_merchant_defined_data20 = req_merchant_defined_data20;
  }
  
  public String getReq_merchant_defined_data21( ) { return req_merchant_defined_data21; }
  
  public void setReq_merchant_defined_data21(String req_merchant_defined_data21) {
    this.req_merchant_defined_data21 = req_merchant_defined_data21;
  }
  
  public String getReq_merchant_defined_data22( ) { return req_merchant_defined_data22; }
  
  public void setReq_merchant_defined_data22(String req_merchant_defined_data22) {
    this.req_merchant_defined_data22 = req_merchant_defined_data22;
  }
  
  public String getReq_merchant_defined_data23( ) { return req_merchant_defined_data23; }
  
  public void setReq_merchant_defined_data23(String req_merchant_defined_data23) {
    this.req_merchant_defined_data23 = req_merchant_defined_data23;
  }
  
  public String getReq_merchant_defined_data24( ) { return req_merchant_defined_data24; }
  
  public void setReq_merchant_defined_data24(String req_merchant_defined_data24) {
    this.req_merchant_defined_data24 = req_merchant_defined_data24;
  }
  
  public String getReq_merchant_defined_data25( ) { return req_merchant_defined_data25; }
  
  public void setReq_merchant_defined_data25(String req_merchant_defined_data25) {
    this.req_merchant_defined_data25 = req_merchant_defined_data25;
  }
  
  public String getReq_merchant_defined_data26( ) { return req_merchant_defined_data26; }
  
  public void setReq_merchant_defined_data26(String req_merchant_defined_data26) {
    this.req_merchant_defined_data26 = req_merchant_defined_data26;
  }
  
  public String getReq_merchant_defined_data27( ) { return req_merchant_defined_data27; }
  
  public void setReq_merchant_defined_data27(String req_merchant_defined_data27) {
    this.req_merchant_defined_data27 = req_merchant_defined_data27;
  }
  
  public String getReq_merchant_defined_data28( ) { return req_merchant_defined_data28; }
  
  public void setReq_merchant_defined_data28(String req_merchant_defined_data28) {
    this.req_merchant_defined_data28 = req_merchant_defined_data28;
  }
  
  public String getReq_merchant_defined_data29( ) { return req_merchant_defined_data29; }
  
  public void setReq_merchant_defined_data29(String req_merchant_defined_data29) {
    this.req_merchant_defined_data29 = req_merchant_defined_data29;
  }
  
  public String getReq_merchant_defined_data30( ) { return req_merchant_defined_data30; }
  
  public void setReq_merchant_defined_data30(String req_merchant_defined_data30) {
    this.req_merchant_defined_data30 = req_merchant_defined_data30;
  }
  
  public String getReq_merchant_defined_data31( ) { return req_merchant_defined_data31; }
  
  public void setReq_merchant_defined_data31(String req_merchant_defined_data31) {
    this.req_merchant_defined_data31 = req_merchant_defined_data31;
  }
  
  public String getReq_merchant_defined_data32( ) { return req_merchant_defined_data32; }
  
  public void setReq_merchant_defined_data32(String req_merchant_defined_data32) {
    this.req_merchant_defined_data32 = req_merchant_defined_data32;
  }
  
  public String getReq_merchant_defined_data33( ) { return req_merchant_defined_data33; }
  
  public void setReq_merchant_defined_data33(String req_merchant_defined_data33) {
    this.req_merchant_defined_data33 = req_merchant_defined_data33;
  }
  
  public String getReq_merchant_defined_data34( ) { return req_merchant_defined_data34; }
  
  public void setReq_merchant_defined_data34(String req_merchant_defined_data34) {
    this.req_merchant_defined_data34 = req_merchant_defined_data34;
  }
  
  public String getReq_merchant_defined_data35( ) { return req_merchant_defined_data35; }
  
  public void setReq_merchant_defined_data35(String req_merchant_defined_data35) {
    this.req_merchant_defined_data35 = req_merchant_defined_data35;
  }
  
  public String getReq_merchant_defined_data36( ) { return req_merchant_defined_data36; }
  
  public void setReq_merchant_defined_data36(String req_merchant_defined_data36) {
    this.req_merchant_defined_data36 = req_merchant_defined_data36;
  }
  
  public String getReq_merchant_defined_data37( ) { return req_merchant_defined_data37; }
  
  public void setReq_merchant_defined_data37(String req_merchant_defined_data37) {
    this.req_merchant_defined_data37 = req_merchant_defined_data37;
  }
  
  public String getReq_merchant_defined_data38( ) { return req_merchant_defined_data38; }
  
  public void setReq_merchant_defined_data38(String req_merchant_defined_data38) {
    this.req_merchant_defined_data38 = req_merchant_defined_data38;
  }
  
  public String getReq_merchant_defined_data39( ) { return req_merchant_defined_data39; }
  
  public void setReq_merchant_defined_data39(String req_merchant_defined_data39) {
    this.req_merchant_defined_data39 = req_merchant_defined_data39;
  }
  
  public String getReq_merchant_defined_data40( ) { return req_merchant_defined_data40; }
  
  public void setReq_merchant_defined_data40(String req_merchant_defined_data40) {
    this.req_merchant_defined_data40 = req_merchant_defined_data40;
  }
  
  public String getReq_merchant_defined_data41( ) { return req_merchant_defined_data41; }
  
  public void setReq_merchant_defined_data41(String req_merchant_defined_data41) {
    this.req_merchant_defined_data41 = req_merchant_defined_data41;
  }
  
  public String getReq_merchant_defined_data42( ) { return req_merchant_defined_data42; }
  
  public void setReq_merchant_defined_data42(String req_merchant_defined_data42) {
    this.req_merchant_defined_data42 = req_merchant_defined_data42;
  }
  
  public String getReq_merchant_defined_data43( ) { return req_merchant_defined_data43; }
  
  public void setReq_merchant_defined_data43(String req_merchant_defined_data43) {
    this.req_merchant_defined_data43 = req_merchant_defined_data43;
  }
  
  public String getReq_merchant_defined_data44( ) { return req_merchant_defined_data44; }
  
  public void setReq_merchant_defined_data44(String req_merchant_defined_data44) {
    this.req_merchant_defined_data44 = req_merchant_defined_data44;
  }
  
  public String getReq_merchant_defined_data45( ) { return req_merchant_defined_data45; }
  
  public void setReq_merchant_defined_data45(String req_merchant_defined_data45) {
    this.req_merchant_defined_data45 = req_merchant_defined_data45;
  }
  
  public String getReq_merchant_defined_data46( ) { return req_merchant_defined_data46; }
  
  public void setReq_merchant_defined_data46(String req_merchant_defined_data46) {
    this.req_merchant_defined_data46 = req_merchant_defined_data46;
  }
  
  public String getReq_merchant_defined_data47( ) { return req_merchant_defined_data47; }
  
  public void setReq_merchant_defined_data47(String req_merchant_defined_data47) {
    this.req_merchant_defined_data47 = req_merchant_defined_data47;
  }
  
  public String getReq_merchant_defined_data48( ) { return req_merchant_defined_data48; }
  
  public void setReq_merchant_defined_data48(String req_merchant_defined_data48) {
    this.req_merchant_defined_data48 = req_merchant_defined_data48;
  }
  
  public String getReq_merchant_defined_data49( ) { return req_merchant_defined_data49; }
  
  public void setReq_merchant_defined_data49(String req_merchant_defined_data49) {
    this.req_merchant_defined_data49 = req_merchant_defined_data49;
  }
  
  public String getReq_merchant_defined_data50( ) { return req_merchant_defined_data50; }
  
  public void setReq_merchant_defined_data50(String req_merchant_defined_data50) {
    this.req_merchant_defined_data50 = req_merchant_defined_data50;
  }
  
  public String getReq_merchant_defined_data51( ) { return req_merchant_defined_data51; }
  
  public void setReq_merchant_defined_data51(String req_merchant_defined_data51) {
    this.req_merchant_defined_data51 = req_merchant_defined_data51;
  }
  
  public String getReq_merchant_defined_data52( ) { return req_merchant_defined_data52; }
  
  public void setReq_merchant_defined_data52(String req_merchant_defined_data52) {
    this.req_merchant_defined_data52 = req_merchant_defined_data52;
  }
  
  public String getReq_merchant_defined_data53( ) { return req_merchant_defined_data53; }
  
  public void setReq_merchant_defined_data53(String req_merchant_defined_data53) {
    this.req_merchant_defined_data53 = req_merchant_defined_data53;
  }
  
  public String getReq_merchant_defined_data54( ) { return req_merchant_defined_data54; }
  
  public void setReq_merchant_defined_data54(String req_merchant_defined_data54) {
    this.req_merchant_defined_data54 = req_merchant_defined_data54;
  }
  
  public String getReq_merchant_defined_data55( ) { return req_merchant_defined_data55; }
  
  public void setReq_merchant_defined_data55(String req_merchant_defined_data55) {
    this.req_merchant_defined_data55 = req_merchant_defined_data55;
  }
  
  public String getReq_merchant_defined_data56( ) { return req_merchant_defined_data56; }
  
  public void setReq_merchant_defined_data56(String req_merchant_defined_data56) {
    this.req_merchant_defined_data56 = req_merchant_defined_data56;
  }
  
  public String getReq_merchant_defined_data57( ) { return req_merchant_defined_data57; }
  
  public void setReq_merchant_defined_data57(String req_merchant_defined_data57) {
    this.req_merchant_defined_data57 = req_merchant_defined_data57;
  }
  
  public String getReq_merchant_defined_data58( ) { return req_merchant_defined_data58; }
  
  public void setReq_merchant_defined_data58(String req_merchant_defined_data58) {
    this.req_merchant_defined_data58 = req_merchant_defined_data58;
  }
  
  public String getReq_merchant_defined_data59( ) { return req_merchant_defined_data59; }
  
  public void setReq_merchant_defined_data59(String req_merchant_defined_data59) {
    this.req_merchant_defined_data59 = req_merchant_defined_data59;
  }
  
  public String getReq_merchant_defined_data60( ) { return req_merchant_defined_data60; }
  
  public void setReq_merchant_defined_data60(String req_merchant_defined_data60) {
    this.req_merchant_defined_data60 = req_merchant_defined_data60;
  }
  
  public String getReq_merchant_defined_data61( ) { return req_merchant_defined_data61; }
  
  public void setReq_merchant_defined_data61(String req_merchant_defined_data61) {
    this.req_merchant_defined_data61 = req_merchant_defined_data61;
  }
  
  public String getReq_merchant_defined_data62( ) { return req_merchant_defined_data62; }
  
  public void setReq_merchant_defined_data62(String req_merchant_defined_data62) {
    this.req_merchant_defined_data62 = req_merchant_defined_data62;
  }
  
  public String getReq_merchant_defined_data63( ) { return req_merchant_defined_data63; }
  
  public void setReq_merchant_defined_data63(String req_merchant_defined_data63) {
    this.req_merchant_defined_data63 = req_merchant_defined_data63;
  }
  
  public String getReq_merchant_defined_data64( ) { return req_merchant_defined_data64; }
  
  public void setReq_merchant_defined_data64(String req_merchant_defined_data64) {
    this.req_merchant_defined_data64 = req_merchant_defined_data64;
  }
  
  public String getReq_merchant_defined_data65( ) { return req_merchant_defined_data65; }
  
  public void setReq_merchant_defined_data65(String req_merchant_defined_data65) {
    this.req_merchant_defined_data65 = req_merchant_defined_data65;
  }
  
  public String getReq_merchant_defined_data66( ) { return req_merchant_defined_data66; }
  
  public void setReq_merchant_defined_data66(String req_merchant_defined_data66) {
    this.req_merchant_defined_data66 = req_merchant_defined_data66;
  }
  
  public String getReq_merchant_defined_data67( ) { return req_merchant_defined_data67; }
  
  public void setReq_merchant_defined_data67(String req_merchant_defined_data67) {
    this.req_merchant_defined_data67 = req_merchant_defined_data67;
  }
  
  public String getReq_merchant_defined_data68( ) { return req_merchant_defined_data68; }
  
  public void setReq_merchant_defined_data68(String req_merchant_defined_data68) {
    this.req_merchant_defined_data68 = req_merchant_defined_data68;
  }
  
  public String getReq_merchant_defined_data69( ) { return req_merchant_defined_data69; }
  
  public void setReq_merchant_defined_data69(String req_merchant_defined_data69) {
    this.req_merchant_defined_data69 = req_merchant_defined_data69;
  }
  
  public String getReq_merchant_defined_data70( ) { return req_merchant_defined_data70; }
  
  public void setReq_merchant_defined_data70(String req_merchant_defined_data70) {
    this.req_merchant_defined_data70 = req_merchant_defined_data70;
  }
  
  public String getReq_merchant_defined_data71( ) { return req_merchant_defined_data71; }
  
  public void setReq_merchant_defined_data71(String req_merchant_defined_data71) {
    this.req_merchant_defined_data71 = req_merchant_defined_data71;
  }
  
  public String getReq_merchant_defined_data72( ) { return req_merchant_defined_data72; }
  
  public void setReq_merchant_defined_data72(String req_merchant_defined_data72) {
    this.req_merchant_defined_data72 = req_merchant_defined_data72;
  }
  
  public String getReq_merchant_defined_data73( ) { return req_merchant_defined_data73; }
  
  public void setReq_merchant_defined_data73(String req_merchant_defined_data73) {
    this.req_merchant_defined_data73 = req_merchant_defined_data73;
  }
  
  public String getReq_merchant_defined_data74( ) { return req_merchant_defined_data74; }
  
  public void setReq_merchant_defined_data74(String req_merchant_defined_data74) {
    this.req_merchant_defined_data74 = req_merchant_defined_data74;
  }
  
  public String getReq_merchant_defined_data75( ) { return req_merchant_defined_data75; }
  
  public void setReq_merchant_defined_data75(String req_merchant_defined_data75) {
    this.req_merchant_defined_data75 = req_merchant_defined_data75;
  }
  
  public String getReq_merchant_defined_data76( ) { return req_merchant_defined_data76; }
  
  public void setReq_merchant_defined_data76(String req_merchant_defined_data76) {
    this.req_merchant_defined_data76 = req_merchant_defined_data76;
  }
  
  public String getReq_merchant_defined_data77( ) { return req_merchant_defined_data77; }
  
  public void setReq_merchant_defined_data77(String req_merchant_defined_data77) {
    this.req_merchant_defined_data77 = req_merchant_defined_data77;
  }
  
  public String getReq_merchant_defined_data78( ) { return req_merchant_defined_data78; }
  
  public void setReq_merchant_defined_data78(String req_merchant_defined_data78) {
    this.req_merchant_defined_data78 = req_merchant_defined_data78;
  }
  
  public String getReq_merchant_defined_data79( ) { return req_merchant_defined_data79; }
  
  public void setReq_merchant_defined_data79(String req_merchant_defined_data79) {
    this.req_merchant_defined_data79 = req_merchant_defined_data79;
  }
  
  public String getReq_merchant_defined_data80( ) { return req_merchant_defined_data80; }
  
  public void setReq_merchant_defined_data80(String req_merchant_defined_data80) {
    this.req_merchant_defined_data80 = req_merchant_defined_data80;
  }
  
  public String getReq_merchant_defined_data81( ) { return req_merchant_defined_data81; }
  
  public void setReq_merchant_defined_data81(String req_merchant_defined_data81) {
    this.req_merchant_defined_data81 = req_merchant_defined_data81;
  }
  
  public String getReq_merchant_defined_data82( ) { return req_merchant_defined_data82; }
  
  public void setReq_merchant_defined_data82(String req_merchant_defined_data82) {
    this.req_merchant_defined_data82 = req_merchant_defined_data82;
  }
  
  public String getReq_merchant_defined_data83( ) { return req_merchant_defined_data83; }
  
  public void setReq_merchant_defined_data83(String req_merchant_defined_data83) {
    this.req_merchant_defined_data83 = req_merchant_defined_data83;
  }
  
  public String getReq_merchant_defined_data84( ) { return req_merchant_defined_data84; }
  
  public void setReq_merchant_defined_data84(String req_merchant_defined_data84) {
    this.req_merchant_defined_data84 = req_merchant_defined_data84;
  }
  
  public String getReq_merchant_defined_data85( ) { return req_merchant_defined_data85; }
  
  public void setReq_merchant_defined_data85(String req_merchant_defined_data85) {
    this.req_merchant_defined_data85 = req_merchant_defined_data85;
  }
  
  public String getReq_merchant_defined_data86( ) { return req_merchant_defined_data86; }
  
  public void setReq_merchant_defined_data86(String req_merchant_defined_data86) {
    this.req_merchant_defined_data86 = req_merchant_defined_data86;
  }
  
  public String getReq_merchant_defined_data87( ) { return req_merchant_defined_data87; }
  
  public void setReq_merchant_defined_data87(String req_merchant_defined_data87) {
    this.req_merchant_defined_data87 = req_merchant_defined_data87;
  }
  
  public String getReq_merchant_defined_data88( ) { return req_merchant_defined_data88; }
  
  public void setReq_merchant_defined_data88(String req_merchant_defined_data88) {
    this.req_merchant_defined_data88 = req_merchant_defined_data88;
  }
  
  public String getReq_merchant_defined_data89( ) { return req_merchant_defined_data89; }
  
  public void setReq_merchant_defined_data89(String req_merchant_defined_data89) {
    this.req_merchant_defined_data89 = req_merchant_defined_data89;
  }
  
  public String getReq_merchant_defined_data90( ) { return req_merchant_defined_data90; }
  
  public void setReq_merchant_defined_data90(String req_merchant_defined_data90) {
    this.req_merchant_defined_data90 = req_merchant_defined_data90;
  }
  
  public String getReq_merchant_defined_data91( ) { return req_merchant_defined_data91; }
  
  public void setReq_merchant_defined_data91(String req_merchant_defined_data91) {
    this.req_merchant_defined_data91 = req_merchant_defined_data91;
  }
  
  public String getReq_merchant_defined_data92( ) { return req_merchant_defined_data92; }
  
  public void setReq_merchant_defined_data92(String req_merchant_defined_data92) {
    this.req_merchant_defined_data92 = req_merchant_defined_data92;
  }
  
  public String getReq_merchant_defined_data93( ) { return req_merchant_defined_data93; }
  
  public void setReq_merchant_defined_data93(String req_merchant_defined_data93) {
    this.req_merchant_defined_data93 = req_merchant_defined_data93;
  }
  
  public String getReq_merchant_defined_data94( ) { return req_merchant_defined_data94; }
  
  public void setReq_merchant_defined_data94(String req_merchant_defined_data94) {
    this.req_merchant_defined_data94 = req_merchant_defined_data94;
  }
  
  public String getReq_merchant_defined_data95( ) { return req_merchant_defined_data95; }
  
  public void setReq_merchant_defined_data95(String req_merchant_defined_data95) {
    this.req_merchant_defined_data95 = req_merchant_defined_data95;
  }
  
  public String getReq_merchant_defined_data96( ) { return req_merchant_defined_data96; }
  
  public void setReq_merchant_defined_data96(String req_merchant_defined_data96) {
    this.req_merchant_defined_data96 = req_merchant_defined_data96;
  }
  
  public String getReq_merchant_defined_data97( ) { return req_merchant_defined_data97; }
  
  public void setReq_merchant_defined_data97(String req_merchant_defined_data97) {
    this.req_merchant_defined_data97 = req_merchant_defined_data97;
  }
  
  public String getReq_merchant_defined_data98( ) { return req_merchant_defined_data98; }
  
  public void setReq_merchant_defined_data98(String req_merchant_defined_data98) {
    this.req_merchant_defined_data98 = req_merchant_defined_data98;
  }
  
  public String getReq_merchant_defined_data99( ) { return req_merchant_defined_data99; }
  
  public void setReq_merchant_defined_data99(String req_merchant_defined_data99) {
    this.req_merchant_defined_data99 = req_merchant_defined_data99;
  }
  
  public String getReq_merchant_defined_data100( ) { return req_merchant_defined_data100; }
  
  public void setReq_merchant_defined_data100(String req_merchant_defined_data100) {
    this.req_merchant_defined_data100 = req_merchant_defined_data100;
  }
}
