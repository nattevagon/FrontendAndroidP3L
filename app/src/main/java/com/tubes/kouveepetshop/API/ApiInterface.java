package com.tubes.kouveepetshop.API;

import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.Model.DetailProcurementDAO;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.DetailTransactionServiceDAO;
import com.tubes.kouveepetshop.Model.EmployeeDAO;
import com.tubes.kouveepetshop.Model.FileProductDAO;
import com.tubes.kouveepetshop.Model.LoginDAO;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.Model.PetTypeDAO;
import com.tubes.kouveepetshop.Model.ProcurementDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.Model.SupplierDAO;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;
import com.tubes.kouveepetshop.Model.TransactionServiceDAO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("produk")
    Call<List<ProductDAO>> getAllProduct();

    @GET("produk")
    Call<List<ProductDAO>> getByProduct(@Query("id_produk") String id_produk);

    @GET("produk/sortbyprice")
    Call<List<ProductDAO>> getSortPrice(@Query("sort") String sort);

    @GET("produk/sortbystock")
    Call<List<ProductDAO>> getSortStock(@Query("sort") String sort);

    @GET("produk/outstock")
    Call<List<ProductDAO>> getOutStock();

    @POST("produk")
    @FormUrlEncoded
    Call<ProductDAO> addProduct(@Field("nama") String nama,
                                @Field("harga") String harga,
                                @Field("minimal") String minimal,
                                @Field("stok") String stok,
                                @Field("satuan") String satuan,
                                @Field("gambar") String gambar);

    @Multipart
    @POST("produk/upload")
    Call<FileProductDAO> upload(@Part MultipartBody.Part file);

    @POST("produk/update")
    @FormUrlEncoded
    Call<ProductDAO> updateProduct(@Field("id_produk") String id_produk,
                                   @Field("nama") String nama,
                                   @Field("harga") String harga,
                                   @Field("minimal") String minimal,
                                   @Field("stok") String stok,
                                   @Field("satuan") String satuan,
                                   @Field("gambar") String gambar);

    @POST("produk/updatestock")
    @FormUrlEncoded
    Call<ProductDAO> updateStockProduct(@Field("id_produk") String id_produk,
                                        @Field("stok") String stok);

    @POST("produk/delete")
    @FormUrlEncoded
    Call<ProductDAO> deleteProduct(@Field("id_produk") String id_produk);

    //=============================================//

    @GET("layanan")
    Call<List<ServiceDAO>> getAllService();

    @GET("layanan")
    Call<List<ServiceDAO>> getByService(@Query("id_layanan") String id_layanan);

    @GET("layanan/detail")
    Call<List<ServiceDAO>> getByIdTS(@Query("id_tl") String id_tl);

    @GET("layanan/detail")
    Call<List<ServiceDAO>> getByIdPetSize(@Query("id_tl") String id_tl,
                                          @Query("id_ukuran_hewan") String id_ukuran_hewan);

    @POST("layanan")
    @FormUrlEncoded
    Call<ServiceDAO> addService(@Field("nama") String nama,
                                @Field("id_ukuran_hewan") String id_ukuran_hewan,
                                @Field("harga") String harga);

    @POST("layanan/update")
    @FormUrlEncoded
    Call<ServiceDAO> updateService(@Field("id_layanan") String id_layanan,
                                   @Field("nama") String nama,
                                   @Field("id_ukuran_hewan") String id_ukuran_hewan,
                                   @Field("harga") String harga);

    @POST("layanan/delete")
    @FormUrlEncoded
    Call<ServiceDAO> deleteService(@Field("id_layanan") String id_layanan);

    //=============================================//

    @GET("customer")
    Call<List<CustomerDAO>> getAllCustomer();

    @GET("customer")
    Call<List<CustomerDAO>> getByCustomer(@Query("id_customer") String id_customer);

    @POST("customer")
    @FormUrlEncoded
    Call<CustomerDAO> addCustomer(@Field("nama") String nama,
                                  @Field("tgl_lahir") String tgl_lahir,
                                  @Field("alamat") String alamat,
                                  @Field("no_telp") String no_telp,
                                  @Field("created_by") String created_by);

    @POST("customer/update")
    @FormUrlEncoded
    Call<CustomerDAO> updateCustomer(@Field("id_customer") String id_customer,
                                     @Field("nama") String nama,
                                     @Field("tgl_lahir") String tgl_lahir,
                                     @Field("alamat") String alamat,
                                     @Field("no_telp") String no_telp,
                                     @Field("updated_by") String updated_by);

    @POST("customer/delete")
    @FormUrlEncoded
    Call<CustomerDAO> deleteCustomer(@Field("id_customer") String id_customer,
                                     @Field("deleted_by") String deleted_by);

    //=============================================//

    @GET("hewan")
    Call<List<PetDAO>> getAllPet();

    @GET("hewan")
    Call<List<PetDAO>> getByPet(@Query("id_hewan") String id_hewan);

    @POST("hewan")
    @FormUrlEncoded
    Call<PetDAO> addPet(@Field("nama") String nama,
                        @Field("id_jenis_hewan") String id_jenis_hewan,
                        @Field("id_ukuran_hewan") String id_ukuran_hewan,
                        @Field("id_customer") String id_customer,
                        @Field("tgl_lahir") String tgl_lahir,
                        @Field("created_by") String created_by);

    @POST("hewan/update")
    @FormUrlEncoded
    Call<PetDAO> updatePet(@Field("id_hewan") String id_hewan,
                           @Field("nama") String nama,
                           @Field("id_jenis_hewan") String id_jenis_hewan,
                           @Field("id_ukuran_hewan") String id_ukuran_hewan,
                           @Field("id_customer") String id_customer,
                           @Field("tgl_lahir") String tgl_lahir,
                           @Field("updated_by") String updated_by);

    @POST("hewan/delete")
    @FormUrlEncoded
    Call<PetDAO> deletePet(@Field("id_hewan") String id_hewan,
                           @Field("deleted_by") String deleted_by);

    //=============================================//

    @GET("jenishewan")
    Call<List<PetTypeDAO>> getAllPetType();

    @GET("jenishewan")
    Call<List<PetTypeDAO>> getByPetType(@Query("id_jenis_hewan") String id_jenis_hewan);

    @POST("jenishewan")
    @FormUrlEncoded
    Call<PetTypeDAO> addPetType(@Field("nama") String nama);

    @POST("jenishewan/update")
    @FormUrlEncoded
    Call<PetTypeDAO> updatePetType(@Field("id_jenis_hewan") String id_jenis_hewan,
                                   @Field("nama") String nama);

    @POST("jenishewan/delete")
    @FormUrlEncoded
    Call<PetTypeDAO> deletePetType(@Field("id_jenis_hewan") String id_jenis_hewan);


    //=============================================//

    @GET("ukuranhewan")
    Call<List<PetSizeDAO>> getAllPetSize();

    @GET("ukuranhewan")
    Call<List<PetSizeDAO>> getByPetSize(@Query("id_ukuran_hewan") String id_ukuran_hewan);

    @POST("ukuranhewan")
    @FormUrlEncoded
    Call<PetSizeDAO> addPetSize(@Field("nama") String nama);

    @POST("ukuranhewan/update")
    @FormUrlEncoded
    Call<PetSizeDAO> updatePetSize(@Field("id_ukuran_hewan") String id_ukuran_hewan,
                                   @Field("nama") String nama);

    @POST("ukuranhewan/delete")
    @FormUrlEncoded
    Call<PetSizeDAO> deletePetSize(@Field("id_ukuran_hewan") String id_ukuran_hewan);

    //=============================================//

    @GET("supplier")
    Call<List<SupplierDAO>> getAllSupplier();

    @GET("supplier")
    Call<List<SupplierDAO>> getBySupplier(@Query("id_supplier") String id_supplier);

    @POST("supplier")
    @FormUrlEncoded
    Call<SupplierDAO> addSupplier(@Field("nama") String nama,
                                  @Field("no_telp") String no_telp,
                                  @Field("alamat") String alamat);

    @POST("supplier/update")
    @FormUrlEncoded
    Call<SupplierDAO> updateSupplier(@Field("id_supplier") String id_supplier,
                                     @Field("nama") String nama,
                                     @Field("no_telp") String no_telp,
                                     @Field("alamat") String alamat);

    @POST("supplier/delete")
    @FormUrlEncoded
    Call<SupplierDAO> deleteSupplier(@Field("id_supplier") String id_supplier);

    //=============================================//

    @GET("pegawai")
    Call<List<EmployeeDAO>> getAllEmployee();

    @GET("pegawai")
    Call<List<EmployeeDAO>> getByIDEmployee(@Query("id_pegawai") String id_pegawai);

    @POST("pegawai/changepassword")
    @FormUrlEncoded
    Call<EmployeeDAO> changePassword(@Field("id_pegawai") String id_pegawai,
                                   @Field("password") String password);

    //=============================================//

    //=============Transaksi Product=============//
    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getAllTransactionProduct();

    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getByIdTransactionProduct(@Query("id_tp") String id_tp);

    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getByCodeTransactionProduct(@Query("kode") String kode);

    @GET("transaksiproduk/codelength")
    Call<List<TransactionProductDAO>> getCodeLengthTransactionProduct(@Query("kode") String kode);

    @GET("transaksiproduk/pembatalan")
    Call<List<TransactionProductDAO>> getAllCanceledTransactionProduct();

    @POST("transaksiproduk")
    @FormUrlEncoded
    Call<TransactionProductDAO> addTransactionProduct(@Field("id_hewan") String id_hewan,
                                                      @Field("id_pegawai_cs") String id_pegawai_cs,
                                                      @Field("kode") String kode,
                                                      @Field("tanggal") String tanggal,
                                                      @Field("sub_total") String sub_total,
                                                      @Field("total_harga") String total_harga,
                                                      @Field("status") String status,
                                                      @Field("created_by") String created_by);

    @POST("transaksiproduk/update")
    @FormUrlEncoded
    Call<TransactionProductDAO> updateTransactionProduct(@Field("id_tp") String id_tp,
                                                         @Field("id_hewan") String id_hewan,
                                                         @Field("updated_by") String updated_by);
    @POST("transaksiproduk/updatesubtotal")
    @FormUrlEncoded
    Call<TransactionProductDAO> updateSubTotalTransactionProduct(@Field("id_tp") String id_tp,
                                                         @Field("sub_total") String sub_total);

    @POST("transaksiproduk/delete")
    @FormUrlEncoded
    Call<TransactionProductDAO> deleteTransactionProduct(@Field("id_tp") String id_tp);

    @POST("transaksiproduk/confirm")
    @FormUrlEncoded
    Call<TransactionProductDAO> confirmTransactionProduct(@Field("id_tp") String id_tp);

    @POST("transaksiproduk/restore")
    @FormUrlEncoded
    Call<TransactionProductDAO> restoreTransactionProduct(@Field("id_tp") String id_tp);

    //==================================================//

    //=============Detail Transaksi=============//

    @GET("detailtransaksiproduk")
    Call<List<DetailTransactionProductDAO>> getAllDetailTP();

    @GET("detailtransaksiproduk")
    Call<List<DetailTransactionProductDAO>> getByIDDetailTP(@Query("id_detail_tp") String id_detail_tp);

    @GET("detailtransaksiproduk")
    Call<List<DetailTransactionProductDAO>> getByIDTPDetailTP(@Query("id_tp") String id_tp);

    @GET("detailtransaksiproduk")
    Call<List<DetailTransactionProductDAO>> getByProdukDTP(@Query("id_tp") String id_tp,
                                                              @Query("id_produk") String id_produk);

    @POST("detailtransaksiproduk")
    @FormUrlEncoded
    Call<DetailTransactionProductDAO> addDetailTP(@Field("id_tp") String id_tp,
                                                  @Field("id_produk") String id_produk,
                                                  @Field("jumlah") String jumlah,
                                                  @Field("total") String total);

    @POST("detailtransaksiproduk/update")
    @FormUrlEncoded
    Call<DetailTransactionProductDAO> updateDetailTP(@Field("id_detail_tp") String id_detail_tp,
                                                     @Field("id_tp") String id_tp,
                                                     @Field("id_produk") String id_produk,
                                                     @Field("jumlah") String jumlah,
                                                     @Field("total") String total);

    @POST("detailtransaksiproduk/delete")
    @FormUrlEncoded
    Call<DetailTransactionProductDAO> deleteDetailTP(@Field("id_detail_tp") String id_detail_tp);

    //==================================================//
    //=============Login=============//

    @POST("login")
    @FormUrlEncoded
    Call<LoginDAO> login(@Field("username") String username,
                         @Field("password") String password);

    //==================================================//

    //=============Transaksi Service=============//
    @GET("transaksilayanan")
    Call<List<TransactionServiceDAO>> getAllTransactionService();

    @GET("transaksilayanan")
    Call<List<TransactionServiceDAO>> getByIdTransactionService(@Query("id_tl") String id_tl);

    @GET("transaksilayanan")
    Call<List<TransactionServiceDAO>> getByCodeTransactionService(@Query("kode") String kode);

    @GET("transaksilayanan/codelength")
    Call<List<TransactionServiceDAO>> getCodeLengthTransactionService(@Query("kode") String kode);

    @GET("transaksilayanan/pembatalan")
    Call<List<TransactionServiceDAO>> getAllCanceledTransactionService();

    @POST("transaksilayanan")
    @FormUrlEncoded
    Call<TransactionServiceDAO> addTransactionService(@Field("id_hewan") String id_hewan,
                                                      @Field("id_pegawai_cs") String id_pegawai_cs,
                                                      @Field("kode") String kode,
                                                      @Field("tanggal") String tanggal,
                                                      @Field("sub_total") String sub_total,
                                                      @Field("total_harga") String total_harga,
                                                      @Field("status") String status,
                                                      @Field("created_by") String created_by);

    @POST("transaksilayanan/update")
    @FormUrlEncoded
    Call<TransactionServiceDAO> updateTransactionService(@Field("id_tl") String id_tl,
                                                         @Field("id_hewan") String id_hewan,
                                                         @Field("updated_by") String updated_by);
    @POST("transaksilayanan/updatesubtotal")
    @FormUrlEncoded
    Call<TransactionServiceDAO> updateSubTotalTransactionService(@Field("id_tl") String id_tl,
                                                                 @Field("sub_total") String sub_total);

    @POST("transaksilayanan/delete")
    @FormUrlEncoded
    Call<TransactionServiceDAO> deleteTransactionService(@Field("id_tl") String id_tl);

    @POST("transaksilayanan/confirm")
    @FormUrlEncoded
    Call<TransactionServiceDAO> confirmTransactionService(@Field("id_tl") String id_tl);

    @POST("transaksilayanan/restore")
    @FormUrlEncoded
    Call<TransactionServiceDAO> restoreTransactionService(@Field("id_tl") String id_tl);

    @POST("transaksilayanan/kirimsms")
    @FormUrlEncoded
    Call<TransactionServiceDAO> sendSMS(@Field("no_hp") String no_hp,
                                        @Field("pesan") String pesan);


    //==================================================//
    //=============Detail Transaksi=============//

    @GET("detailtransaksilayanan")
    Call<List<DetailTransactionServiceDAO>> getAllDetailTS();

    @GET("detailtransaksilayanan")
    Call<List<DetailTransactionServiceDAO>> getByIDDetailTS(@Query("id_detail_tl") String id_detail_tl);

    @GET("detailtransaksilayanan")
    Call<List<DetailTransactionServiceDAO>> getByIDTSDetailTS(@Query("id_tl") String id_tl);

    @GET("detailtransaksilayanan")
    Call<List<DetailTransactionServiceDAO>> getBylayananDTS(@Query("id_tl") String id_tl,
                                                            @Query("id_layanan") String id_layanan);

    @POST("detailtransaksilayanan")
    @FormUrlEncoded
    Call<DetailTransactionServiceDAO> addDetailTS(@Field("id_tl") String id_tl,
                                                  @Field("id_layanan") String id_layanan,
                                                  @Field("jumlah") String jumlah,
                                                  @Field("total") String total);

    @POST("detailtransaksilayanan/update")
    @FormUrlEncoded
    Call<DetailTransactionServiceDAO> updateDetailTS(@Field("id_detail_tl") String id_detail_tl,
                                                     @Field("jumlah") String jumlah,
                                                     @Field("total") String total);

    @POST("detailtransaksilayanan/delete")
    @FormUrlEncoded
    Call<DetailTransactionServiceDAO> deleteDetailTS(@Field("id_detail_tl") String id_detail_tl);

    //==================================================//

    //=============Procurement=============//
    @GET("pengadaan")
    Call<List<ProcurementDAO>> getAllProcurement();

    @GET("pengadaan")
    Call<List<ProcurementDAO>> getByIdProcurement(@Query("id_pengadaan") String id_pengadaan);

    @GET("pengadaan")
    Call<List<ProcurementDAO>> getByCodeProcurement(@Query("kode") String kode);

    @GET("pengadaan/codelength")
    Call<List<ProcurementDAO>> getCodeLengthProcurement(@Query("kode") String kode);

    @GET("pengadaan/showby")
    Call<List<ProcurementDAO>> getAllByStatusProcurement(@Query("status") String status);

    @POST("pengadaan")
    @FormUrlEncoded
    Call<ProcurementDAO> addProcurement(@Field("id_supplier") String id_supplier,
                                        @Field("kode") String kode,
                                        @Field("tanggal") String tanggal,
                                        @Field("status") String status,
                                        @Field("total_harga") String total_harga);

    @POST("pengadaan/update")
    @FormUrlEncoded
    Call<ProcurementDAO> updateProcurement(@Field("id_pengadaan") String id_tp,
                                           @Field("id_supplier") String id_supplier,
                                           @Field("kode") String kode,
                                           @Field("tanggal") String tanggal);
    @POST("pengadaan/updatetotal")
    @FormUrlEncoded
    Call<ProcurementDAO> updateTotalProcurement(@Field("id_pengadaan") String id_pengadaan,
                                                @Field("total_harga") String total_harga);

    @POST("pengadaan/delete")
    @FormUrlEncoded
    Call<ProcurementDAO> deleteProcurement(@Field("id_pengadaan") String id_pengadaan);

    @POST("pengadaan/order")
    @FormUrlEncoded
    Call<ProcurementDAO> confirmProcurement(@Field("id_pengadaan") String id_pengadaan);

    @POST("pengadaan/done")
    @FormUrlEncoded
    Call<ProcurementDAO> doneProcurement(@Field("id_pengadaan") String id_pengadaan);

    @POST("pengadaan/return")
    @FormUrlEncoded
    Call<ProcurementDAO> returnProcurement(@Field("id_pengadaan") String id_pengadaan);

    //==================================================//

    //=============Procurement=============//
    @GET("detailpengadaan")
    Call<List<DetailProcurementDAO>> getAllDetailP();

    @GET("detailpengadaan")
    Call<List<DetailProcurementDAO>> getByIdDetailP(@Query("id_detail_p") String id_detail_p);

    @GET("detailpengadaan")
    Call<List<DetailProcurementDAO>> getByIdPDetailP(@Query("id_pengadaan") String id_pengadaan);

    @GET("detailpengadaan")
    Call<List<DetailProcurementDAO>> getByProdukDP(@Query("id_pengadaan") String id_pengadaan,
                                                     @Query("id_produk") String id_produk);

    @POST("detailpengadaan")
    @FormUrlEncoded
    Call<DetailProcurementDAO> addDetailP(@Field("id_pengadaan") String id_pengadaan,
                                            @Field("id_produk") String id_produk,
                                            @Field("jumlah") String jumlah,
                                            @Field("total") String total);

    @POST("detailpengadaan/update")
    @FormUrlEncoded
    Call<DetailProcurementDAO> updateDetailP(@Field("id_detail_p") String id_detail_p,
                                             @Field("id_pengadaan") String id_pengadaan,
                                             @Field("id_produk") String id_produk,
                                             @Field("jumlah") String jumlah,
                                             @Field("total") String total);

    @POST("detailpengadaan/delete")
    @FormUrlEncoded
    Call<DetailProcurementDAO> deleteDetailP(@Field("id_detail_p") String id_detail_p);

    //==================================================//
    //=============Receipt Procurement=============//

    @GET("struk")
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Query("id_pengadaan") String id_pengadaan);

    //==================================================//

}
