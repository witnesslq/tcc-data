package com.framework.base.redis;

/**
 * @author fengwei //
 * @date 16/4/18 11:15
 *
 * 缓存KEY的定义
 */
public class CacheKey {

    /**
     * sip_group  <5
     */
    public static final String SIP_GROUP = "cti-link.sip_group.list";

    /**
     * sip_media_server  < 1000
     */
    public static final String SIP_MEDIA_SERVER_IP_ADDR = "cti-link.sip_media_server.ip_addr.%s";

    public static final String SIP_MEDIA_SERVER_ID = "cti-link.sip_media_server.id.%d";

    public static final String SIP_MEDIA_SERVER = "cit-link.sip_media_server";

    /**
     * sip_proxy  <1000
     */
    public static final String SIP_PROXY_IP_ADDR = "cti-link.sip_proxy.ip_addr.%s";

    public static final String SIP_PROXY = "cti-link.sip_proxy";

    /**
     * router  <1000
     */
    public static final String ROUTER_ROUTERSET_ID = "cti-link.router.routerset_id.%d";

    /**
     * gateway  <1000
     */
    public static final String GATEWAY = "cti-link.gateway";

    public static final String GATEWAY_NAME = "cti-link.gateway.name.%s";

    /**
     * system_setting  <1000
     */
    public static final String SYSTEM_SETTING = "cti-link.system_setting";

    public static final String SYSTEM_SETTING_NAME = "cti-link.system_setting.name.%s";

    /**
     * public_moh  <1000
     */
    public static final String PUBLIC_MOH_NAME = "cti-link.public_moh.name.%s";

    /**
     * area_code  30w
     */
    public static final String AREA_CODE_PREFIX = "cti-link.area_code.prefix.%s";

    /**
     * area_code  30w
     */
    public static final String AREA_CODE = "tcc-conf.area_code.%s";

    /**
     * entity  <1000
     */
    public static final String ENTITY_ENTERPRISE_ID = "cti-link.entity.%d";

    public static final String ENTITY_ENTERPRISE_ACTIVE = "cti-link.entity.active";

    /**
     * trunk  <10000
     */
    public static final String TRUNK_NUMBER_TRUNK = "cti-link.trunk.number_trunk.%s";

    public static final String TRUNK_ENTERPRISE_ID_FIRST = "cti-link.trunk.%s.first";

    /**
     * enterprise_hotline  <10000
     */
    public static final String ENTERPRISE_HOTLINE_ENTERPRISE_ID_NUMBER_TRUNK = "cti-link.enterprise_hotline.%d.number_trunk.%s";

    public static final String ENTERPRISE_HOTLINE_ENTERPRISE_ID = "cti-link.enterprise_hotline.%d";

    /**
     * enterprise_clid  <1000
     */
    public static final String ENTERPRISE_CLID_ENTERPRISE_ID = "cti-link.enterprise_clid.%d";

    /**
     * ivr_profile  <1000
     */
    public static final String IVR_PROFILE_ENTERPRISE_ID_ID = "cti-link.ivr_profile.%d.id.%d";

    public static final String IVR_PROFILE_ENTERPRISE_ID = "cti-link.ivr_profile.%d";

    public static final String IVR_PROFILE = "cti-link.ivr_profile";

    /**
     * enterprise_setting  <1000
     */
    public static final String ENTERPRISE_SETTING_ENTERPRISE_ID_NAME = "cti-link.enterprise_setting.%d.name.%s";

    public static final String ENTERPRISE_SETTING_ENTERPRISE_ID = "cti-link.enterprise_setting.%s";

    /**
     * agent  <20000
     */
    public static final String AGENT_ENTERPRISE_ID_CNO = "cti-link.agent.%d.cno.%s";

    public static final String AGENT_ENTERPRISE_ID = "cti-link.agent.%s";

    public static final String AGENT = "cti-link.agent";

    /**
     * agent_tel <100000
     */
    public static final String AGENT_TEL_ENTERPRISE_ID_CNO = "cti-link.agent_tel.%d.cno.%s";

    public static final String AGENT_TEL_ENTERPRISE_ID = "cti-link.agent_tel.%s";

    /**
     * queue  <10000
     */
    public static final String QUEUE_ENTERPRISE_ID_QNO = "cti-link.queue.%d.qno.%s";

    public static final String QUEUE_ENTERPRISE_ID = "cti-link.queue.%s";

    /**
     * queue_member  <1000
     */
    public static final String QUEUE_MEMBER_ENTERPRISE_ID_QNO = "cti-link.queue_member.enterprise_id.%d.qno.%s";

    public static final String QUEUE_MEMBER_ENTERPRISE_ID_QNO_CNO = "cti-link.queue_member.enterprise_id.qno.cno.%d.%s.%s";

    public static final String QUEUE_MEMBER_ENTERPRISE_ID_CNO = "cti-link.queue_member.enterprise_id.cno.%d.%s";

    /**
     * enterprise_ivr  <1000
     */
    public static final String ENTERPRISE_IVR_ENTERPRISE_ID_IVR_ID = "cti-link.enterprise_ivr.%d.ivr_id.%d";

    /**
     * enterprise_ivr  <1000
     */
    public static final String ENTERPRISE_IVR_ANCHOR_ENTERPRISE_ID_IVR_ID = "cti-link.enterprise_ivr_anchor.%d.ivr_id.%d";

    /**
     * enterprise_ivr_router  <1000
     */
    public static final String ENTERPRISE_IVR_ROUTER_ENTERPRISE_ID = "cti-link.enterprise_ivr_router.%d";

    /**
     * enterprise_time  <1000
     */
    public static final String ENTERPRISE_TIME_ENTERPRISE_ID_ID = "cti-link.enterprise_time.%d.id.%d";

    /**
     * enterprise_area  <1000
     */
    public static final String ENTERPRISE_AREA_ENTERPRISE_ID_GROUP_ID_AREA_CODE = "cti-link.enterprise_area.%d.group_id.%d.area_code.%s";

    /**
     * enterprise_area_group  <1000
     */
    public static final String ENTERPRISE_AREA_GROUP_ENTERPRISE_ID_ID = "cti-link.enterprise_area_group.%d.id.%d";

    /**
     * enterprise_hangup_action  <1000
     */
    public static final String ENTERPRISE_HANGUP_ACTION_ENTERPRISE_ID_TYPE = "cti-link.enterprise_hangup_action.%d.type.%d";

    /**
     * enterprise_hangup_set  <1000
     */
    public static final String ENTERPRISE_HANGUP_SET_ENTERPRISE_ID_TYPE = "cti-link.enterprise_hangup_set.%d.type.%d";

    /**
     * enterprise_moh  <1000
     */
    public static final String ENTERPRISE_MOH_NAME = "cti-link.enterprise_moh.name.%s";

    /**
     * enterprise_router  < 1000
     */
    public static final String ENTERPRISE_ROUTER_ENTERPRISE_ID = "cti-link.enterprise_router.%d";

    /**
     * enterprise_voicemail  <1000
     */
    public static final String ENTERPRISE_VOICEMAIL_ENTERPRISE_ID_ID = "cti-link.enterprise_voicemail.%d.id.%d";

    /**
     * enterprise_investigation  <1000
     */
    public static final String ENTERPRISE_INVESTIGATION_ENTERPRISE_ID = "cti-link.enterprise_investigation_enterprise_id.%d";

    /**
     * restrict_tel  10w
     */
    public static final String RESTRICT_TEL_ENTERPRISE_ID_TYPE_RESTRICT_TYPE_TEL = "cti-link.restrict_tel.%d.type.%d.restrict_type.%d.tel.%s";

    /**
     * tel_set  <10000
     */
    public static final String TEL_SET_ENTERPRISE_ID_TSNO = "cti-link.tel_set.%d.tsno.%s";

    /**
     * tel_set_tel  <10000
     */
    public static final String TEL_SET_TEL_ENTERPRISE_TSNO = "cti-link.tel_set_tel.%d.tsno.%s";
    
    /**
     * agent_skill < 100000
     */
    public static final String AGNET_SKILL_ENTERPRISE_ID_AGENT_ID = "cti-link.agent_skill.%d.agent_id.%d";
    
    /**
     * queue_skill < 10000
     */
    public static final String QUEUE_SKILL_ENTERPRISE_ID_AGENT_ID = "cti-link.queue_skill.%d.agent_id.%d";
    
    
}
