# TCC-DATA

    ##TCC-DATA-CDR （通话纪录）

    功能：

      	1.数据来源，sub redis（由cti-link-data pub数据到redis）、cti-link-data-API提供基于KV的接口
    	2.入库到关系型数据库中，每个enterprise_id一张表（在TCCBOSS开户的时候，写入开户数据到TCC-conf，然后由TCC－conf调用TCC-DATA-CDR接口创建表）
    	3.查询来电纪录、外呼纪录、留言纪录、满意度调查纪录、预约纪录
    	4.查询座席日志、绑定电话日志
    	5.支持多租户（帐户体系来自于TCC-conf）
    	6.支持多级权限系统的数据查询（帐户体系来自于TCC-conf）
    	
    相关表：

    	1.cdr_ib_
    	2.cdr_ib_detail
    	3.cdr_ob
    	4.cdr_ob_detail
    	5.enterprise_voicemail
    	6.investigation_record
    	7.order_call_back
    	8.agent_action          (来自于sub cti-link_data log_queue)
    	9.agent_bindTel_action	
    	
    性能：

    	1.数据支持最大保留三年
    	2.数据量支持千万级
    	3.查询耗时，最大值为3s
    	4.集群方案


    ##TCC-DATA-Status（实时报表）

    功能：
    	1.当日当前座席工作量实时查看	（外呼通话总时长、座席来点数、座席接听率、……）
    	2.当日当前队列实时统计	（队列接听率、总通话时长、队列来电接听数、总进入队列数、进入队列来点数、……）
    	3.实时来电客户数
    	4.实时来电排队数
    	5.其它待定
    	
    相关表：
    	1.无，此子系统不需要数据库，数据全部都是实时的
    	2.数据来源sub redis (cti-link_data pub redis)  or 调用cti-link_data-API然后进行计算
    	
    性能：

        实时、集群、无状态

    ##TCC-DATA-BR（Business Report）

    	1.先调研一下FineReport后再来做设计
