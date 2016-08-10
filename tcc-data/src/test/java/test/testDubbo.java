package test;

import com.tinet.ctilink.conf.ApiResult;
import com.tinet.ctilink.conf.model.CtiLinkAgent;
import com.tinet.ctilink.conf.request.AgentWithSkill;
import com.tinet.ctilink.conf.service.v1.CtiLinkAgentService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by peizj on 16-6-28.
 */
public class testDubbo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "file:///home/peizj/GitHub/Tinet/tcc-conf/tcc-conf/src/main/config/spring/applicationContext-dubbo.xml");
        context.start();
        CtiLinkAgentService agentService = (CtiLinkAgentService) context.getBean("ctiLinkAgentService"); // 获取远程服务代理
        Integer enterpriseId=7000001;

//        AgentListRequest agentListRequest = new AgentListRequest();
//        agentListRequest.setEnterpriseId(enterpriseId);
//        agentListRequest.setLimit(10);
//        agentListRequest.setOffset(0);

        CtiLinkAgent agent=new CtiLinkAgent();
        agent.setEnterpriseId(enterpriseId);
        agent.setCno("2000");
        agent.setId(3);
        ApiResult<AgentWithSkill> list= agentService.getAgent(agent);
        System.out.println(list.getData().toString());
    }
}
