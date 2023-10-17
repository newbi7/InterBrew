package com.toyproject.internbrew_backend.inquiry.entity;

import com.toyproject.internbrew_backend.inquiry.dto.InquiryDto;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryUserDto;
import com.toyproject.internbrew_backend.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Table(name = "INQUIRY_TB")
public class Inquiry {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "pk_inquiry_no", columnDefinition = "VARCHAR(50) NOT NULL")
    private String inquiryNo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_no", referencedColumnName = "pk_user_no", columnDefinition = "VARCHAR(50) NOT NULL")
    private User user;

    @Column(name = "inquiry_contents", columnDefinition = "VARCHAR(1000)")
    private String inquiryContents;

    @Column(name = "inquiry_created_at", columnDefinition = "DATETIME")
    private LocalDateTime inquiryCreatedAt;

    @Column(name = "inquiry_updated_at", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime inquiryUpdatedAt;

    @Column(name = "inquiry_user_create", columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String inquiryUserCreate;

    @Column(name = "inquiry_user_update", columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String inquiryUserUpdate;

    @Column(name = "inquiry_user_status", columnDefinition = "VARCHAR(4) DEFAULT 'Y'")
    private String inquiryUserStatus;

    public void changeStatus(String inquiryUserStatus) {
        this.inquiryUserStatus = inquiryUserStatus;
    }

    private Inquiry (InquiryUserDto dto) {
        this.inquiryNo = dto.getInquiryNo();
        this.user = dto.getUser();
        this.inquiryContents = dto.getInquiryContents();
        this.inquiryCreatedAt = dto.getInquiryCreatedAt();
        this.inquiryUpdatedAt = dto.getInquiryUpdatedAt();
        this.inquiryUserCreate = dto.getInquiryUserCreate();
        this.inquiryUserUpdate = dto.getInquiryUserUpdate();
        this.inquiryUserStatus = dto.getInquiryUserStatus();
    }

    public static Inquiry on(InquiryUserDto dto) { return new Inquiry(dto); }
}
