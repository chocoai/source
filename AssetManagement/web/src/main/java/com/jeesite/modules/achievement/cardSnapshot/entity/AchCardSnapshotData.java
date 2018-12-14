package com.jeesite.modules.achievement.cardSnapshot.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.entity.AchCardData;
import com.jeesite.modules.achievement.cardscore.entity.AchCardScore;
import com.jeesite.modules.achievement.cardscoremodify.entity.AchCardScoreModify;
import com.jeesite.modules.achievement.cardsynthetical.entity.AchCardSynthetical;
import com.jeesite.modules.achievement.usermission.entity.AchCardMission;
import com.jeesite.modules.achievement.usertarget.entity.AchUserTarget;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Table(name="ach_card_snapshot", alias="a", columns={
        @Column(name="card_snapshot_code", attrName="cardSnapshotCode", label="单据编号", isPK=true),
        @Column(name="card_code", attrName="cardCode", label="绩效卡编号"),
        @Column(name="user_id", attrName="userId", label="员工编码"),
        @Column(name="user_name", attrName="userName", label="员工名称", queryType=QueryType.LIKE),
        @Column(name="depart_code", attrName="departCode", label="部门编码"),
        @Column(name="depart_name", attrName="departName", label="部门名称", queryType=QueryType.LIKE),
        @Column(name="post_code", attrName="postCode", label="岗位编码"),
        @Column(name="post_name", attrName="postName", label="岗位名称", queryType=QueryType.LIKE),
        @Column(name="examine_month", attrName="examineMonth", label="考核月份"),
        @Column(includeEntity=DataEntity.class),
        @Column(name="data_status", attrName="dataStatus", label="数据状态"),
        @Column(name="data_type", attrName="dataType", label="数据状态"),
        @Column(name="data", attrName="data", label="快照内容"),},
        joinTable = {
                @JoinTable(type = JoinTable.Type.JOIN, entity = AchCard.class, alias = "b",
                        on = "a.card_code = b.card_code", attrName = "achCard",
                        columns = {
                                @Column(name="card_code", attrName="cardCode", label="单据编号", isPK=true),
                                @Column(name="examine_month", attrName="examineMonth", label="考核月份"),
                                @Column(name="examined_staff_code", attrName="examinedStaffCode", label="被考核人"),
                                @Column(name="depart_code", attrName="departCode", label="所属部门编码"),
                                @Column(name="depart_name", attrName="departName", label="所属部门名称"),
                                @Column(name="first_depart_code", attrName="firstDepartCode", label="一级部门编码"),
                                @Column(name="first_depart_name", attrName="firstDepartName", label="一级部门名称"),
                                @Column(name="post_code", attrName="postCode", label="岗位编码"),
                                @Column(name="had_follower", attrName="hadFollower", label="是否有下属"),
                                @Column(name="assessor_code", attrName="assessorCode", label="考核人"),
                                @Column(name="examine_weight", attrName="examineWeight", label="考核权重"),
                                @Column(name="examine_score", attrName="examineScore", label="考核得分"),
                                @Column(name="second_superior_score", attrName="secondSuperiorScore", label="上上级调整分值"),
                                @Column(name="final_score", attrName="finalScore", label="最终得分"),
                                @Column(name="strong_point", attrName="strongPoint", label="优点"),
                                @Column(name="weak_point", attrName="weakPoint", label="弱点"),
                                @Column(name="feedback_type", attrName="feedbackType", label="绩效反馈形式"),
                                @Column(name="feedback_remark", attrName="feedbackRemark", label="绩效反馈备注"),
                                @Column(name="feedback_minutes", attrName="feedbackMinutes", label="绩效沟通分钟数"),
                                @Column(includeEntity=DataEntity.class),
                                @Column(name="audit_by", attrName="auditBy", label="审核人"),
                                @Column(name="audit_date", attrName="auditDate", label="审核时间"),
                                @Column(name="second_superior_remark", attrName="secondSuperiorRemark", label="上上级调整备注"),
                                @Column(name="hr_score", attrName="hrScore", label="HR调整分数"),
                                @Column(name="hr_score_remark", attrName="hrScoreRemark", label="HR调整分数备注"),
                                @Column(name="self_doing_well", attrName="selfDoingWell", label="自评优点"),
                                @Column(name="self_can_be_better", attrName="selfCanBeBetter", label="自评缺点"),
                                @Column(name="data_status", attrName="dataStatus", label="数据状态"),
                                @Column(name="second_superior_code", attrName="secondSuperiorCode", label="上上级编号"),
                                @Column(name="second_superior_name", attrName="secondSuperiorName", label="上上级姓名", queryType=QueryType.LIKE),
                                @Column(name="interview_summary", attrName="interviewSummary", label="面谈总结"),
                                @Column(name="interview_instruction", attrName="interviewInstruction", label="面谈说明"),
                        }),
                @JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AchUserTarget.class, alias="c",
                        on="a.card_code = c.card_code", attrName = "achUserTargets",
                        columns={
                                @Column(name="user_target_code", attrName="userTargetCode", label="用户指标编号", isPK=true),
                                @Column(name="card_code", attrName="cardCode", label="单据编号"),
                                @Column(name="user_id", attrName="userId", label="用户ID"),
                                @Column(name="target_code", attrName="targetCode", label="指标编号"),
                                @Column(name="target_name", attrName="targetName", label="指标名称", queryType=QueryType.LIKE),
                                @Column(name="target_level", attrName="targetLevel", label="指标级别"),
                                @Column(name="target_type", attrName="targetType", label="指标类型"),
                                @Column(name="standard_score", attrName="standardScore", label="标准分数"),
                                @Column(name="description", attrName="description", label="指标内容描述"),
                                @Column(name="formula", attrName="formula", label="统计公式"),
                                @Column(name="coefficient_range", attrName="coefficientRange", label="系数范围"),
                                @Column(name="bottom", attrName="bottom", label="底线值"),
                                @Column(name="basics_down", attrName="basicsDown", label="基础下行"),
                                @Column(name="basic_aims", attrName="basicAims", label="基础目标"),
                                @Column(name="basics_upstream", attrName="basicsUpstream", label="基础上行"),
                                @Column(name="challenge_goal", attrName="challengeGoal", label="挑战目标"),
                                @Column(name="target_setting_content", attrName="targetSettingContent", label="目标设定备注"),
                                @Column(name="data_sources", attrName="dataSources", label="数据来源/计算过程"),
                                @Column(name="score_coefficient", attrName="scoreCoefficient", label="得分系数"),
                                @Column(name="relevance", attrName="relevance", label="相关性"),
                                @Column(name="actual_score", attrName="actualScore", label="本期实际分数"),
                                @Column(name="examined_staff_remark", attrName="examinedStaffRemark", label="被考核人备注"),
                                @Column(name="assessor_remark", attrName="assessorRemark", label="考核人备注"),
                                @Column(includeEntity=DataEntity.class),
                                @Column(name="last_score", attrName="lastScore", label="上一期得分"),
                                @Column(name="last_second_score", attrName="lastSecondScore", label="上两期得分"),
                                @Column(name="audit_by", attrName="auditBy", label="审核人"),
                                @Column(name="last_third_score", attrName="lastThirdScore", label="上三期得分"),
                                @Column(name="audit_date", attrName="auditDate", label="审核时间"),
                                @Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
                                @Column(name="data_status", attrName="dataStatus", label="数据状态"),
                                @Column(name="is_achievement", attrName="isAchievement", label="是否业绩指标", comment="是否业绩指标：0：默认否；1：是"),
                        }),
                @JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AchCardMission.class, alias="d",
                        on="a.card_code = d.card_code", attrName = "achCardMissions",
                        columns={
                                @Column(name="mission_code", attrName="missionCode", label="任务编号", isPK=true),
                                @Column(name="card_code", attrName="cardCode", label="绩效卡编号"),
                                @Column(name="user_id", attrName="userId", label="用户ID"),
                                @Column(name="mission_name", attrName="missionName", label="任务名称", queryType=QueryType.LIKE),
                                @Column(name="mission_type", attrName="missionType", label="任务类型"),
                                @Column(name="standard_score", attrName="standardScore", label="标准得分"),
                                @Column(name="goal", attrName="goal", label="任务目标"),
                                @Column(name="description", attrName="description", label="任务描述"),
                                @Column(name="examine_standard", attrName="examineStandard", label="考核标准"),
                                @Column(name="mission_situation", attrName="missionSituation", label="任务情况"),
                                @Column(name="reson", attrName="reson", label="原因说明"),
                                @Column(name="evaluate_reson", attrName="evaluateReson", label="评级/加扣分理由"),
                                @Column(name="self_evaluation", attrName="selfEvaluation", label="自评级别/加扣分"),
                                @Column(name="self_evaluation_score", attrName="selfEvaluationScore", label="自评得分"),
                                @Column(name="self_evaluation_remark", attrName="selfEvaluationRemark", label="自评备注"),
                                @Column(name="examine_evaluation", attrName="examineEvaluation", label="考核级别/加扣分"),
                                @Column(name="examine_evaluation_score", attrName="examineEvaluationScore", label="考核得分"),
                                @Column(name="examine_evaluation_remark", attrName="examineEvaluationRemark", label="主考备注"),
                                @Column(includeEntity=DataEntity.class),
                                @Column(name="audit_by", attrName="auditBy", label="审核人"),
                                @Column(name="audit_date", attrName="auditDate", label="审核时间"),
                                @Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
                                @Column(name="data_status", attrName="dataStatus", label="数据状态"),
                        }),
                @JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AchCardSynthetical.class, alias="e",
                        on="a.card_code = e.card_code",
                        columns={
                                @Column(name="card_synthetical_code", attrName="cardSyntheticalCode", label="绩效卡综合管理编码", isPK=true),
                                @Column(name="card_code", attrName="cardCode", label="绩效卡编号"),
                                @Column(name="user_id", attrName="userId", label="用户ID"),
                                @Column(name="synthetical_code", attrName="syntheticalCode", label="综合管理编码"),
                                @Column(name="examine_type", attrName="examineType", label="考核类型"),
                                @Column(name="ach_quota", attrName="achQuota", label="绩效指标"),
                                @Column(name="examine_quota", attrName="examineQuota", label="考核标准"),
                                @Column(name="standard_score", attrName="standardScore", label="标准分"),
                                @Column(name="high_score", attrName="highScore", label="最高加分"),
                                @Column(name="self_evaluation_score", attrName="selfEvaluationScore", label="自评得分"),
                                @Column(name="self_evaluation_remark", attrName="selfEvaluationRemark", label="自评备注"),
                                @Column(name="examine_evaluation_score", attrName="examineEvaluationScore", label="考核得分"),
                                @Column(name="examine_evaluation_remark", attrName="examineEvaluationRemark", label="主考备注"),
                                @Column(includeEntity=DataEntity.class),
                                @Column(name="audit_by", attrName="auditBy", label="审核人"),
                                @Column(name="audit_date", attrName="auditDate", label="审核时间"),
                                @Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
                                @Column(name="data_status", attrName="dataStatus", label="数据状态"),
                        }),
                @JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AchCardScore.class, alias="f",
                        on="a.card_code = f.card_code",
                        columns={
                                @Column(name="card_score_code", attrName="cardScoreCode", label="评分编号", isPK=true),
                                @Column(name="card_code", attrName="cardCode", label="绩效卡编号"),
                                @Column(name="user_id", attrName="userId", label="员工编码"),
                                @Column(name="examine_score", attrName="examineScore", label="考核得分"),
                                @Column(name="examine_name", attrName="examineName", label="绩效指标名称", queryType=QueryType.LIKE),
                                @Column(name="evaluation_remark", attrName="evaluationRemark", label="自评汇总"),
                                @Column(name="examine_type", attrName="examineType", label="考核类型"),
                                @Column(name="remark", attrName="remark", label="自评汇总"),
                                @Column(name="standard_score", attrName="standardScore", label="标准分值"),
                                @Column(name="last_examine_score", attrName="lastExamineScore", label="上期得分"),
                                @Column(name="examined_staff_code", attrName="examinedStaffCode", label="被考核人"),
                                @Column(name="evaluation_score", attrName="evaluationScore", label="自评得分"),
                                @Column(includeEntity=DataEntity.class),
                                @Column(name="audit_by", attrName="auditBy", label="审核人"),
                                @Column(name="audit_date", attrName="auditDate", label="审核时间"),
                                @Column(name="score_group", attrName="scoreGroup", label="所属评分组"),
                                @Column(name="data_status", attrName="dataStatus", label="数据状态"),
                        }),
        }, orderBy=" a.update_date DESC"
)
public class AchCardSnapshotData extends DataEntity<AchCardSnapshotData> {

    private AchCard achCard;    //绩效卡
    private List<AchCardScore> achCardScores;   //绩效卡评分列表
    private List<AchUserTarget> achUserTargets; //绩效卡指标列表
    private List<AchCardMission> achCardMissions;   //绩效卡任务列表
    private List<AchCardSynthetical> achCardSyntheticals;   //绩效卡综合列表
    private List<AchCardScoreModify> achCardScoreModifies;  //绩效卡加扣分列表

    private static final long serialVersionUID = 1L;
    private String cardSnapshotCode;		// 单据编号
    private String cardCode;		// 绩效卡编号
    private String userId;		// 员工编码
    private String userName;		// 员工名称
    private String departCode;		// 部门编码
    private String departName;		// 部门名称
    private String postCode;		// 岗位编码
    private String postName;		// 岗位名称
    private String examineMonth;		// 考核月份
    private String dataStatus;		// 数据状态
    private String dataType;		// 数据状态
    private String data;		// 快照内容



    private AchCardData achCardData;    //绩效卡关联信息

    public AchCardSnapshotData() {
        this(null);
    }

    public AchCardSnapshotData(String id){
        super(id);
    }

    public String getCardSnapshotCode() {
        return cardSnapshotCode;
    }

    public void setCardSnapshotCode(String cardSnapshotCode) {
        this.cardSnapshotCode = cardSnapshotCode;
    }

    @NotBlank(message="绩效卡编号不能为空")
    @Length(min=0, max=64, message="绩效卡编号长度不能超过 64 个字符")
    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Length(min=0, max=64, message="员工编码长度不能超过 64 个字符")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Length(min=0, max=64, message="员工名称长度不能超过 64 个字符")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Length(min=0, max=64, message="部门编码长度不能超过 64 个字符")
    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    @Length(min=0, max=64, message="部门名称长度不能超过 64 个字符")
    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    @Length(min=0, max=64, message="岗位编码长度不能超过 64 个字符")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Length(min=0, max=64, message="岗位名称长度不能超过 64 个字符")
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Length(min=0, max=10, message="考核月份长度不能超过 10 个字符")
    public String getExamineMonth() {
        return examineMonth;
    }

    public void setExamineMonth(String examineMonth) {
        this.examineMonth = examineMonth;
    }

    @Length(min=0, max=4, message="数据状态长度不能超过 4 个字符")
    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Length(min=0, max=4, message="数据状态长度不能超过 4 个字符")
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AchCard getAchCard() {
        return achCard;
    }

    public void setAchCard(AchCard achCard) {
        this.achCard = achCard;
    }

    public List<AchCardScore> getAchCardScores() {
        return achCardScores;
    }

    public void setAchCardScores(List<AchCardScore> achCardScores) {
        this.achCardScores = achCardScores;
    }

    public List<AchUserTarget> getAchUserTargets() {
        return achUserTargets;
    }

    public void setAchUserTargets(List<AchUserTarget> achUserTargets) {
        this.achUserTargets = achUserTargets;
    }

    public List<AchCardMission> getAchCardMissions() {
        return achCardMissions;
    }

    public void setAchCardMissions(List<AchCardMission> achCardMissions) {
        this.achCardMissions = achCardMissions;
    }

    public List<AchCardSynthetical> getAchCardSyntheticals() {
        return achCardSyntheticals;
    }

    public void setAchCardSyntheticals(List<AchCardSynthetical> achCardSyntheticals) {
        this.achCardSyntheticals = achCardSyntheticals;
    }

    public List<AchCardScoreModify> getAchCardScoreModifies() {
        return achCardScoreModifies;
    }

    public void setAchCardScoreModifies(List<AchCardScoreModify> achCardScoreModifies) {
        this.achCardScoreModifies = achCardScoreModifies;
    }

    public AchCardData getAchCardData() {
        return achCardData;
    }

    public void setAchCardData(AchCardData achCardData) {
        this.achCardData = achCardData;
    }
}
