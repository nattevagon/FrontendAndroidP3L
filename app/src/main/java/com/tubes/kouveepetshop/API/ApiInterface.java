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
    Call<List<ProductDAO>> getByProduct(@Query("id_produk") String id_produk);

    @GET("produk/sortbyprice")
    Call<List<ProductDAO>> getSortPrice();

    @GET("produk/sortbystock")
    Call<List<ProductDAO>> getSortStock();

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

    @POST("produk/delete")
    @FormUrlEncoded
    Call<ProductDAO> deleteProduct(@Field("id_produk") String id_produk);

    //=============================================//

    @GET("layanan")
    Call<List<ServiceDAO>> getAllService();

    @GET("layanan")
    Call<List<ServiceDAO>> getByService(@Query("id_layanan") String id_layanan);

    @POST("layanan")
    @FormUrlEncoded
    Call<ServiceDAO> addService(@Field("nama") String nama);

    @POST("layanan/update")
    @FormUrlEncoded
    Call<ServiceDAO> updateService(@Field("id_layanan") String id_layanan,
                                   @Field("nama") String nama);

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
                                  @Field("no_telp") String no_telp);

    @POST("customer/update")
    @FormUrlEncoded
    Call<CustomerDAO> updateCustomer(@Field("id_customer") String id_customer,
                                     @Field("nama") String nama,
                                     @Field("tgl_lahir") String tgl_lahir,
                                     @Field("alamat") String alamat,
                                     @Field("no_telp") String no_telp);

    @POST("customer/delete")
    @FormUrlEncoded
    Call<CustomerDAO> deleteCustomer(@Field("id_customer") String id_customer);

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
                        @Field("tgl_lahir") String tgl_lahir);

    @POST("hewan/update")
    @FormUrlEncoded
    Call<PetDAO> updatePet(@Field("id_hewan") String id_hewan,
                           @Field("nama") String nama,
                           @Field("id_jenis_hewan") String id_jenis_hewan,
                           @Field("id_ukuran_hewan") String id_ukuran_hewan,
                           @Field("id_customer") String id_customer,
                           @Field("tgl_lahir") String tgl_lahir);

    @POST("hewan/delete")
    @FormUrlEncoded
    Call<PetDAO> deletePet(@Field("id_hewan") String id_hewan);

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

    //=============Transaksi=============//
    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getAllTransactionProduct();

    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getByIdTransactionProduct(@Query("id_tp") String id_tp);

    @GET("transaksiproduk")
    Call<List<TransactionProductDAO>> getByCodeTransactionProduct(@Query("kode") String kode);

    @GET("transaksiproduk/codelength")
    Call<List<TransactionProductDAO>> getCodeLengthTransactionProduct(@Query("kode") String kode);

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
                                                         @Field("id_pegawai_cs") String id_pegawai,
                                                         @Field("kode") String kode,
                                                         @Field("tanggal") String tanggal,
                                                         @Field("sub_total") String sub_total,
                                                         @Field("total_harga") String total_harga,
                                                         @Field("status") String status,
                                                         @Field("created_by") String created_by);

    @POST("transaksiproduk/delete")
    @FormUrlEncoded
    Call<TransactionProductDAO> deleteTransactionProduct(@Field("id_tp") String id_tp);

    @POST("transaksiproduk/confirm")
    @FormUrlEncoded
    Call<TransactionProductDAO> confirmTransactionProduct(@Field("id_tp") String id_tp);

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

}
