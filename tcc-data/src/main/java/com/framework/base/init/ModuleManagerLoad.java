package com.framework.base.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 15/1/8 14:39
 * Email:johnny_lx@yahoo.com
 */
public class ModuleManagerLoad {

    private static final Logger logger = LoggerFactory.getLogger(ModuleManagerLoad.class);

    public void moduleStart(){
        try {
//            TccConfResourceGroupService tccConfResourceGroupService = (TccConfResourceGroupService) PlatformContextListener.getContext().getBean("tccConfResourceGroupService");
//            Constants.RESOURCE_GROUP_LIST = tccConfResourceGroupService.queryResourceGroupList();
//            logger.debug("将数据库tcc_conf_resource_group表中的所有数据同步到内存中×××××Init TccConfResourceGroup cache");
//            TccConfResourcePageService tccConfResourcePageService = (TccConfResourcePageService)PlatformContextListener.getContext().getBean("tccConfResourcePageService");
//            Constants.RESOURCE_PAGE_LIST = tccConfResourcePageService.queryResourcePageList();
//            logger.debug("将数据库tcc_conf_resource_page表中的所有数据同步到内存中×××××Init TccConfResourcePage cache");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
