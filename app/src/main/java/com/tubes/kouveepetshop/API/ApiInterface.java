package com.tubes.kouveepetshop.API;

import com.tubes.kouveepetshop.Model.CustomerDAO;
import com.tubes.kouveepetshop.Model.DetailTransactionProductDAO;
import com.tubes.kouveepetshop.Model.FileProductDAO;
import com.tubes.kouveepetshop.Model.PetDAO;
import com.tubes.kouveepetshop.Model.PetSizeDAO;
import com.tubes.kouveepetshop.Model.PetTypeDAO;
import com.tubes.kouveepetshop.Model.ProductDAO;
import com.tubes.kouveepetshop.Model.ServiceDAO;
import com.tubes.kouveepetshop.Model.SupplierDAO;
import com.tubes.kouveepetshop.Model.TransactionProductDAO;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("produk")
    Call<List<ProductDAO>> getAllProduct();

    @GET("produk")
    Call<List<ProductDAO>> getByProduct(@Query("id_produk") String ID_PRODUK);

    @GET("produk/sortbyprice")
    Call<List<ProductDAO>> getSortPrice();

    @GET("produk/sortbystock")
    Call<List<ProductDAO>> getSortStock();

    @POST("produk")
    @FormUrlEncoded
    Call<ProductDAO> addProduct(@Field("nama") String NAMA,
                                @Field("harga") String HARGA,
                                @Field("minimal") String MINIMAL,
                                @Field("stok") String STOK,
                                @Field("satuan") String SATUAN,
                                @Field("gambar") String GAMBAR);

    @Multipart
    @POST("produk/upload")
    Call<FileProductDAO> upload(@Part MultipartBody.Part file);

    @POST("produk/update")
    @FormUrlEncoded
    Call<ProductDAO> updateProduct(@Field("id_produk") String ID_PRODUK,
                                   @Field("nama") String NAMA,
                                   @Field("harga") String HARGA,
                                   @Field("minimal") String MINIMAL,
                                   @Field("stok") String STOK,
                                   @Field("satuan") String SATUAN,
                                   @Field("gambar") String GAMBAR);

    @POST("produk/delete")
    @FormUrlEncoded
    Call<ProductDAO> deleteProduct(@Field("id_produk") String ID_PRODUK);

    //=============================================//

    @GET("layanan")
    Call<List<ServiceDAO>> getAllService();

    @GET("layanan")
    Call<List<ServiceDAO>> getByService(@Query("id_layanan") String ID_LAYANAN);

    @POST("layanan")
    @FormUrlEncoded
    Call<ServiceDAO> addService(@Field("nama") String NAMA);

    @POST("layanan/update")
    @FormUrlEncoded
    Call<ServiceDAO> updateService(@Field("id_layanan") String ID_LAYANAN,
                                   @Field("nama") String NAMA);

    @POST("layanan/delete")
    @FormUrlEncoded
    Call<ServiceDAO> deleteService(@Field("id_layanan") String ID_LAYANAN);

    //=============================================//

    @GET("customer")
    Call<List<CustomerDAO>> getAllCustomer();

    @GET("customer")
    Call<List<CustomerDAO>> getByCustomer(@Query("id_customer") String ID_CUSTOMER);

    @POST("customer")
    @FormUrlEncoded
    Call<CustomerDAO> addCustomer(@Field("nama") String NAMA,
                                  @Field("tgl_lahir") String TGL_LAHIR,
                                  @Field("alamat") String ALAMAT,
                                  @Field("no_telp") String NO_TELP);

    @POST("customer/update")
    @FormUrlEncoded
    Call<CustomerDAO> updateCustomer(@Field("id_customer") String ID_CUSTOMER,
                                     @Field("nama") String NAMA,
                                     @Field("tgl_lahir") String TGL_LAHIR,
                                     @Field("alamat") String ALAMAT,
                                     @Field("no_telp") String NO_TELP);

    @POST("customer/delete")
    @FormUrlEncoded
    Call<CustomerDAO> deleteCustomer(@Field("id_customer") String ID_CUSTOMER);

    //=============================================//

    @GET("hewan")
    Call<List<PetDAO>> getAllPet();

    @GET("hewan")
    Call<List<PetDAO>> getByPet(@Query("id_hewan") String ID_HEWAN);

    @POST("hewan")
    @FormUrlEncoded
    Call<PetDAO> addPet(@Field("nama") String NAMA,
                        @Field("id_jenis_hewan") String ID_JENIS_HEWAN,
                        @Field("id_ukuran_hewan") String ID_UKURAN_HEWAN,
                        @Field("id_customer") String ID_CUSTOMER,
                        @Field("tgl_lahir") String TGL_LAHIR);

    @POST("hewan/update")
    @FormUrlEncoded
    Call<PetDAO> updatePet(@Field("id_hewan") String ID_HEWAN,
                           @Field("nama") String NAMA,
                           @Field("id_jenis_hewan") String ID_JENIS_HEWAN,
                           @Field("id_ukuran_hewan") String ID_UKURAN_HEWAN,
                           @Field("id_customer") String ID_CUSTOMER,
                           @Field("tgl_lahir") String TGL_LAHIR);

    @POST("hewan/delete")
    @FormUrlEncoded
    Call<PetDAO> deletePet(@Field("id_hewan") String ID_HEWAN);

    //=============================================//

    @GET("jenishewan")
    Call<List<PetTypeDAO>> getAllPetType();

    @GET("jenishewan")
    Call<List<PetTypeDAO>> getByPetType(@Query("id_jenis_hewan") String ID_JENIS_HEWAN);

    @POST("jenishewan")
    @FormUrlEncoded
    Call<PetTypeDAO> addPetType(@Field("nama") String NAMA);

    @POST("jenishewan/update")
    @FormUrlEncoded
    Call<PetTypeDAO> updatePetType(@Field("id_jenis_hewan") String ID_JENIS_HEWAN,
                                   @Field("nama") String NAMA);

    @POST("jenishewan/delete")
    @FormUrlEncoded
    Call<PetTypeDAO> deletePetType(@Field("id_jenis_hewan") String ID_JENIS_HEWAN);


    //=============================================//

    @GET("ukuranhewan")
    Call<List<PetSizeDAO>> getAllPetSize();

    @GET("ukuranhewan")
    Call<List<PetSizeDAO>> getByPetSize(@Query("id_ukuran_hewan") String ID_UKURAN_HEWAN);

    @POST("ukuranhewan")
    @FormUrlEncoded
    Call<PetSizeDAO> addPetSize(@Field("nama") String NAMA);

    @POST("ukuranhewan/update")
    @FormUrlEncoded
    Call<PetSizeDAO> updatePetSize(@Field("id_ukuran_hewan") String ID_UKURAN_HEWAN,
                                   @Field("nama") String NAMA);

    @POST("ukuranhewan/delete")
    @FormUrlEncoded
    Call<PetSizeDAO> deletePetSize(@Field("id_ukuran_hewan") String ID_UKURAN_HEWAN);

    //=============================================//

    @GET("supplier")
    Call<List<SupplierDAO>> getAllSupplier();

    @GET("supplier")
    Call<List<SupplierDAO>> getBySupplier(@Query("id_supplier") String ID_SUPPLIER);

    @POST("supplier")
    @FormUrlEncoded
    Call<SupplierDAO> addSupplier(@Field("nama") String NAMA,
                                  @Field("no_telp") String NO_TELP,
                                  @Field("alamat") String ALAMAT);

    @POST("supplier/update")
    @FormUrlEncoded
    Call<SupplierDAO> updateSupplier(@Field("id_supplier") String ID_SUPPLIER,
                                     @Field("nama") String NAMA,
                                     @Field("no_telp") String NO_TELP,
                                     @Field("alamat") String ALAMAT);

    @POST("supplier/delete")
    @FormUrlEncoded
    Call<SupplierDAO> deleteSupplier(@Field("id_supplier") String ID_SUPPLIER);

    //=============================================//

    //=============Transaksi=============//
    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getAllTransactionProduct();

    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getByIdTransactionProduct(@Query("id_tp") String ID_TP);

    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getByCodeTransactionProduct(@Query("kode") String KODE);

    @GET("transaksiproduk/codelength")
    Call<List<TransactionProductDAO>> getCodeLengthTransactionProduct(@Query("kode") String KODE);

    @POST("transaksiproduk")
    @FormUrlEncoded
    Call<TransactionProductDAO> addTransactionProduct(@Field("id_hewan") String ID_HEWAN,
                                                      @Field("id_pegawai_cs") String ID_PEGAWAI_CS,
                                                      @Field("kode") String KODE,
                                                      @Field("tanggal") String TANGGAL,
                                                      @Field("sub_total") String SUB_TOTAL,
                                                      @Field("total_harga") String TOTAL_HARGA,
                                                      @Field("status") String STATUS,
                                                      @Field("created_by") String CREATED_BY);

    @POST("transaksiproduk/update")
    @FormUrlEncoded
    Call<TransactionProductDAO> updateTransactionProduct(@Field("id_tp") String ID_TP,
                                                         @Field("id_hewan") String ID_HEWAN,
                                                         @Field("id_pegawai_cs") String ID_PEGAWAI_CS,
                                                         @Field("kode") String KODE,
                                                         @Field("tanggal") String TANGGAL,
                                                         @Field("sub_total") String SUB_TOTAL,
                                                         @Field("total_harga") String TOTAL_HARGA);

    @POST("transaksiproduk/delete")
    @FormUrlEncoded
    Call<TransactionProductDAO> deleteTransactionProduct(@Field("id_tp") String ID_TP);

    @POST("transaksiproduk/confirm")
    @FormUrlEncoded
    Call<TransactionProductDAO> confirmTransactionProduct(@Field("id_tp") String ID_TP);

    //==================================================//

    //=============Detail Transaksi=============//

    @GET("detailtransaksiproduk")
    Call<List<DetailTransactionProductDAO>> getAllDetailTP();

    @GET("detailtransaksiproduk")
    Call<List<DetailTransactionProductDAO>> getByDetailTP(@Query("id_detail_tp") String ID_DETAIL_TP);

    @POST("detailtransaksiproduk")
    @FormUrlEncoded
    Call<DetailTransactionProductDAO> addDetailTP(@Field("id_tp") String ID_TP,
                                                  @Field("id_produk") String ID_PRODUK,
                                                  @Field("jumlah") String JUMLAH,
                                                  @Field("total") String TOTAL);

    @POST("detailtransaksiproduk/update")
    @FormUrlEncoded
    Call<DetailTransactionProductDAO> updateDetailTP(@Field("id_detail_tp") String ID_DETAIL_TP,
                                                     @Field("id_tp") String ID_TP,
                                                     @Field("id_produk") String ID_PRODUK,
                                                     @Field("jumlah") String JUMLAH,
                                                     @Field("total") String TOTAL);

    @POST("detailtransaksiproduk/delete")
    @FormUrlEncoded
    Call<DetailTransactionProductDAO> deleteDetailTP(@Field("id_detail_tp") String ID_DETAIL_TP);

    //==================================================//

}
