
package com.buaa.yyg.baidupager.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Img {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("tags")
    @Expose
    private List<String> tags = new ArrayList<String>();
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("fromPageTitle")
    @Expose
    private String fromPageTitle;
    @SerializedName("column")
    @Expose
    private String column;
    @SerializedName("parentTag")
    @Expose
    private String parentTag;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("downloadUrl")
    @Expose
    private String downloadUrl;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("imageWidth")
    @Expose
    private int imageWidth;
    @SerializedName("imageHeight")
    @Expose
    private int imageHeight;
    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;
    @SerializedName("thumbnailWidth")
    @Expose
    private int thumbnailWidth;
    @SerializedName("thumbnailHeight")
    @Expose
    private int thumbnailHeight;
    @SerializedName("thumbLargeWidth")
    @Expose
    private int thumbLargeWidth;
    @SerializedName("thumbLargeHeight")
    @Expose
    private int thumbLargeHeight;
    @SerializedName("thumbLargeUrl")
    @Expose
    private String thumbLargeUrl;
    @SerializedName("thumbLargeTnWidth")
    @Expose
    private int thumbLargeTnWidth;
    @SerializedName("thumbLargeTnHeight")
    @Expose
    private int thumbLargeTnHeight;
    @SerializedName("thumbLargeTnUrl")
    @Expose
    private String thumbLargeTnUrl;
    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("siteLogo")
    @Expose
    private String siteLogo;
    @SerializedName("siteUrl")
    @Expose
    private String siteUrl;
    @SerializedName("fromUrl")
    @Expose
    private String fromUrl;
    @SerializedName("isBook")
    @Expose
    private String isBook;
    @SerializedName("bookId")
    @Expose
    private String bookId;
    @SerializedName("objUrl")
    @Expose
    private String objUrl;
    @SerializedName("shareUrl")
    @Expose
    private String shareUrl;
    @SerializedName("setId")
    @Expose
    private String setId;
    @SerializedName("albumId")
    @Expose
    private String albumId;
    @SerializedName("isAlbum")
    @Expose
    private int isAlbum;
    @SerializedName("albumName")
    @Expose
    private String albumName;
    @SerializedName("albumNum")
    @Expose
    private int albumNum;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("isVip")
    @Expose
    private int isVip;
    @SerializedName("isDapei")
    @Expose
    private int isDapei;
    @SerializedName("dressId")
    @Expose
    private String dressId;
    @SerializedName("dressBuyLink")
    @Expose
    private String dressBuyLink;
    @SerializedName("dressPrice")
    @Expose
    private int dressPrice;
    @SerializedName("dressDiscount")
    @Expose
    private int dressDiscount;
    @SerializedName("dressExtInfo")
    @Expose
    private String dressExtInfo;
    @SerializedName("dressTag")
    @Expose
    private String dressTag;
    @SerializedName("dressNum")
    @Expose
    private int dressNum;
    @SerializedName("objTag")
    @Expose
    private String objTag;
    @SerializedName("dressImgNum")
    @Expose
    private int dressImgNum;
    @SerializedName("hostName")
    @Expose
    private String hostName;
    @SerializedName("pictureId")
    @Expose
    private String pictureId;
    @SerializedName("pictureSign")
    @Expose
    private String pictureSign;
    @SerializedName("dataSrc")
    @Expose
    private String dataSrc;
    @SerializedName("contentSign")
    @Expose
    private String contentSign;
    @SerializedName("albumDi")
    @Expose
    private String albumDi;
    @SerializedName("canAlbumId")
    @Expose
    private String canAlbumId;
    @SerializedName("albumObjNum")
    @Expose
    private String albumObjNum;
    @SerializedName("appId")
    @Expose
    private String appId;
    @SerializedName("photoId")
    @Expose
    private String photoId;
    @SerializedName("fromName")
    @Expose
    private int fromName;
    @SerializedName("fashion")
    @Expose
    private String fashion;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 
     * @param desc
     *     The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 
     * @return
     *     The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * 
     * @param tags
     *     The tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * 
     * @return
     *     The owner
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * 
     * @param owner
     *     The owner
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * 
     * @return
     *     The fromPageTitle
     */
    public String getFromPageTitle() {
        return fromPageTitle;
    }

    /**
     * 
     * @param fromPageTitle
     *     The fromPageTitle
     */
    public void setFromPageTitle(String fromPageTitle) {
        this.fromPageTitle = fromPageTitle;
    }

    /**
     * 
     * @return
     *     The column
     */
    public String getColumn() {
        return column;
    }

    /**
     * 
     * @param column
     *     The column
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * 
     * @return
     *     The parentTag
     */
    public String getParentTag() {
        return parentTag;
    }

    /**
     * 
     * @param parentTag
     *     The parentTag
     */
    public void setParentTag(String parentTag) {
        this.parentTag = parentTag;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The downloadUrl
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * 
     * @param downloadUrl
     *     The downloadUrl
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    /**
     * 
     * @return
     *     The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 
     * @param imageUrl
     *     The imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 
     * @return
     *     The imageWidth
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * 
     * @param imageWidth
     *     The imageWidth
     */
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * 
     * @return
     *     The imageHeight
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * 
     * @param imageHeight
     *     The imageHeight
     */
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    /**
     * 
     * @return
     *     The thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * 
     * @param thumbnailUrl
     *     The thumbnailUrl
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * 
     * @return
     *     The thumbnailWidth
     */
    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    /**
     * 
     * @param thumbnailWidth
     *     The thumbnailWidth
     */
    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    /**
     * 
     * @return
     *     The thumbnailHeight
     */
    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    /**
     * 
     * @param thumbnailHeight
     *     The thumbnailHeight
     */
    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    /**
     * 
     * @return
     *     The thumbLargeWidth
     */
    public int getThumbLargeWidth() {
        return thumbLargeWidth;
    }

    /**
     * 
     * @param thumbLargeWidth
     *     The thumbLargeWidth
     */
    public void setThumbLargeWidth(int thumbLargeWidth) {
        this.thumbLargeWidth = thumbLargeWidth;
    }

    /**
     * 
     * @return
     *     The thumbLargeHeight
     */
    public int getThumbLargeHeight() {
        return thumbLargeHeight;
    }

    /**
     * 
     * @param thumbLargeHeight
     *     The thumbLargeHeight
     */
    public void setThumbLargeHeight(int thumbLargeHeight) {
        this.thumbLargeHeight = thumbLargeHeight;
    }

    /**
     * 
     * @return
     *     The thumbLargeUrl
     */
    public String getThumbLargeUrl() {
        return thumbLargeUrl;
    }

    /**
     * 
     * @param thumbLargeUrl
     *     The thumbLargeUrl
     */
    public void setThumbLargeUrl(String thumbLargeUrl) {
        this.thumbLargeUrl = thumbLargeUrl;
    }

    /**
     * 
     * @return
     *     The thumbLargeTnWidth
     */
    public int getThumbLargeTnWidth() {
        return thumbLargeTnWidth;
    }

    /**
     * 
     * @param thumbLargeTnWidth
     *     The thumbLargeTnWidth
     */
    public void setThumbLargeTnWidth(int thumbLargeTnWidth) {
        this.thumbLargeTnWidth = thumbLargeTnWidth;
    }

    /**
     * 
     * @return
     *     The thumbLargeTnHeight
     */
    public int getThumbLargeTnHeight() {
        return thumbLargeTnHeight;
    }

    /**
     * 
     * @param thumbLargeTnHeight
     *     The thumbLargeTnHeight
     */
    public void setThumbLargeTnHeight(int thumbLargeTnHeight) {
        this.thumbLargeTnHeight = thumbLargeTnHeight;
    }

    /**
     * 
     * @return
     *     The thumbLargeTnUrl
     */
    public String getThumbLargeTnUrl() {
        return thumbLargeTnUrl;
    }

    /**
     * 
     * @param thumbLargeTnUrl
     *     The thumbLargeTnUrl
     */
    public void setThumbLargeTnUrl(String thumbLargeTnUrl) {
        this.thumbLargeTnUrl = thumbLargeTnUrl;
    }

    /**
     * 
     * @return
     *     The siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * 
     * @param siteName
     *     The siteName
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * 
     * @return
     *     The siteLogo
     */
    public String getSiteLogo() {
        return siteLogo;
    }

    /**
     * 
     * @param siteLogo
     *     The siteLogo
     */
    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo;
    }

    /**
     * 
     * @return
     *     The siteUrl
     */
    public String getSiteUrl() {
        return siteUrl;
    }

    /**
     * 
     * @param siteUrl
     *     The siteUrl
     */
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    /**
     * 
     * @return
     *     The fromUrl
     */
    public String getFromUrl() {
        return fromUrl;
    }

    /**
     * 
     * @param fromUrl
     *     The fromUrl
     */
    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    /**
     * 
     * @return
     *     The isBook
     */
    public String getIsBook() {
        return isBook;
    }

    /**
     * 
     * @param isBook
     *     The isBook
     */
    public void setIsBook(String isBook) {
        this.isBook = isBook;
    }

    /**
     * 
     * @return
     *     The bookId
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * 
     * @param bookId
     *     The bookId
     */
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    /**
     * 
     * @return
     *     The objUrl
     */
    public String getObjUrl() {
        return objUrl;
    }

    /**
     * 
     * @param objUrl
     *     The objUrl
     */
    public void setObjUrl(String objUrl) {
        this.objUrl = objUrl;
    }

    /**
     * 
     * @return
     *     The shareUrl
     */
    public String getShareUrl() {
        return shareUrl;
    }

    /**
     * 
     * @param shareUrl
     *     The shareUrl
     */
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    /**
     * 
     * @return
     *     The setId
     */
    public String getSetId() {
        return setId;
    }

    /**
     * 
     * @param setId
     *     The setId
     */
    public void setSetId(String setId) {
        this.setId = setId;
    }

    /**
     * 
     * @return
     *     The albumId
     */
    public String getAlbumId() {
        return albumId;
    }

    /**
     * 
     * @param albumId
     *     The albumId
     */
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    /**
     * 
     * @return
     *     The isAlbum
     */
    public int getIsAlbum() {
        return isAlbum;
    }

    /**
     * 
     * @param isAlbum
     *     The isAlbum
     */
    public void setIsAlbum(int isAlbum) {
        this.isAlbum = isAlbum;
    }

    /**
     * 
     * @return
     *     The albumName
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * 
     * @param albumName
     *     The albumName
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * 
     * @return
     *     The albumNum
     */
    public int getAlbumNum() {
        return albumNum;
    }

    /**
     * 
     * @param albumNum
     *     The albumNum
     */
    public void setAlbumNum(int albumNum) {
        this.albumNum = albumNum;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The isVip
     */
    public int getIsVip() {
        return isVip;
    }

    /**
     * 
     * @param isVip
     *     The isVip
     */
    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    /**
     * 
     * @return
     *     The isDapei
     */
    public int getIsDapei() {
        return isDapei;
    }

    /**
     * 
     * @param isDapei
     *     The isDapei
     */
    public void setIsDapei(int isDapei) {
        this.isDapei = isDapei;
    }

    /**
     * 
     * @return
     *     The dressId
     */
    public String getDressId() {
        return dressId;
    }

    /**
     * 
     * @param dressId
     *     The dressId
     */
    public void setDressId(String dressId) {
        this.dressId = dressId;
    }

    /**
     * 
     * @return
     *     The dressBuyLink
     */
    public String getDressBuyLink() {
        return dressBuyLink;
    }

    /**
     * 
     * @param dressBuyLink
     *     The dressBuyLink
     */
    public void setDressBuyLink(String dressBuyLink) {
        this.dressBuyLink = dressBuyLink;
    }

    /**
     * 
     * @return
     *     The dressPrice
     */
    public int getDressPrice() {
        return dressPrice;
    }

    /**
     * 
     * @param dressPrice
     *     The dressPrice
     */
    public void setDressPrice(int dressPrice) {
        this.dressPrice = dressPrice;
    }

    /**
     * 
     * @return
     *     The dressDiscount
     */
    public int getDressDiscount() {
        return dressDiscount;
    }

    /**
     * 
     * @param dressDiscount
     *     The dressDiscount
     */
    public void setDressDiscount(int dressDiscount) {
        this.dressDiscount = dressDiscount;
    }

    /**
     * 
     * @return
     *     The dressExtInfo
     */
    public String getDressExtInfo() {
        return dressExtInfo;
    }

    /**
     * 
     * @param dressExtInfo
     *     The dressExtInfo
     */
    public void setDressExtInfo(String dressExtInfo) {
        this.dressExtInfo = dressExtInfo;
    }

    /**
     * 
     * @return
     *     The dressTag
     */
    public String getDressTag() {
        return dressTag;
    }

    /**
     * 
     * @param dressTag
     *     The dressTag
     */
    public void setDressTag(String dressTag) {
        this.dressTag = dressTag;
    }

    /**
     * 
     * @return
     *     The dressNum
     */
    public int getDressNum() {
        return dressNum;
    }

    /**
     * 
     * @param dressNum
     *     The dressNum
     */
    public void setDressNum(int dressNum) {
        this.dressNum = dressNum;
    }

    /**
     * 
     * @return
     *     The objTag
     */
    public String getObjTag() {
        return objTag;
    }

    /**
     * 
     * @param objTag
     *     The objTag
     */
    public void setObjTag(String objTag) {
        this.objTag = objTag;
    }

    /**
     * 
     * @return
     *     The dressImgNum
     */
    public int getDressImgNum() {
        return dressImgNum;
    }

    /**
     * 
     * @param dressImgNum
     *     The dressImgNum
     */
    public void setDressImgNum(int dressImgNum) {
        this.dressImgNum = dressImgNum;
    }

    /**
     * 
     * @return
     *     The hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 
     * @param hostName
     *     The hostName
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * 
     * @return
     *     The pictureId
     */
    public String getPictureId() {
        return pictureId;
    }

    /**
     * 
     * @param pictureId
     *     The pictureId
     */
    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    /**
     * 
     * @return
     *     The pictureSign
     */
    public String getPictureSign() {
        return pictureSign;
    }

    /**
     * 
     * @param pictureSign
     *     The pictureSign
     */
    public void setPictureSign(String pictureSign) {
        this.pictureSign = pictureSign;
    }

    /**
     * 
     * @return
     *     The dataSrc
     */
    public String getDataSrc() {
        return dataSrc;
    }

    /**
     * 
     * @param dataSrc
     *     The dataSrc
     */
    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }

    /**
     * 
     * @return
     *     The contentSign
     */
    public String getContentSign() {
        return contentSign;
    }

    /**
     * 
     * @param contentSign
     *     The contentSign
     */
    public void setContentSign(String contentSign) {
        this.contentSign = contentSign;
    }

    /**
     * 
     * @return
     *     The albumDi
     */
    public String getAlbumDi() {
        return albumDi;
    }

    /**
     * 
     * @param albumDi
     *     The albumDi
     */
    public void setAlbumDi(String albumDi) {
        this.albumDi = albumDi;
    }

    /**
     * 
     * @return
     *     The canAlbumId
     */
    public String getCanAlbumId() {
        return canAlbumId;
    }

    /**
     * 
     * @param canAlbumId
     *     The canAlbumId
     */
    public void setCanAlbumId(String canAlbumId) {
        this.canAlbumId = canAlbumId;
    }

    /**
     * 
     * @return
     *     The albumObjNum
     */
    public String getAlbumObjNum() {
        return albumObjNum;
    }

    /**
     * 
     * @param albumObjNum
     *     The albumObjNum
     */
    public void setAlbumObjNum(String albumObjNum) {
        this.albumObjNum = albumObjNum;
    }

    /**
     * 
     * @return
     *     The appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 
     * @param appId
     *     The appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 
     * @return
     *     The photoId
     */
    public String getPhotoId() {
        return photoId;
    }

    /**
     * 
     * @param photoId
     *     The photoId
     */
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    /**
     * 
     * @return
     *     The fromName
     */
    public int getFromName() {
        return fromName;
    }

    /**
     * 
     * @param fromName
     *     The fromName
     */
    public void setFromName(int fromName) {
        this.fromName = fromName;
    }

    /**
     * 
     * @return
     *     The fashion
     */
    public String getFashion() {
        return fashion;
    }

    /**
     * 
     * @param fashion
     *     The fashion
     */
    public void setFashion(String fashion) {
        this.fashion = fashion;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
