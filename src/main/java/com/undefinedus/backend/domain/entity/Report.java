package com.undefinedus.backend.domain.entity;

import com.undefinedus.backend.domain.enums.ReportStatus;
import com.undefinedus.backend.domain.enums.ReportTargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report extends BaseEntity {
    
    // === ID === //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // === 신고자 === //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false) // 신고를 한 사람의 ID
    private Member reporter;  // 신고한 사람
    
    // === 신고 대상 === //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportee_id", nullable = false) // 신고를 당한 사람의 ID
    private Member reported;  // 신고 당한 사람
    
    // === 신고 대상 정보 === //
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportTargetType targetType;  // 신고 대상 타입 (Discussion, Comment 등)
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReportStatus status = ReportStatus.PENDING; // 처리 상태 (PENDING, ACCEPTED, REJECTED)
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id", updatable = false)
    private Discussion discussion;  // 신고 당한 토론 게시물 // comment가 존재하면 null
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_comment_id", updatable = false)
    private DiscussionComment comment;  // 신고 당한 토론 댓글 게시물 // discussion가 존재하면 null
}