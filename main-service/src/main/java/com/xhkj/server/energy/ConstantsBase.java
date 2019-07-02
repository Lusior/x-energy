package com.xhkj.server.energy;

public class ConstantsBase {

	public static final String UUID_MACHINE_ID = "uuid.machine.id";
	/** 菜单顶层父ID(最高) */
	public static final String MENU_PID_HIGHEST = "0000";
	/** 菜单叶节点 */
	public static final String MENU_LEAF_NODE = "1";

	/** 角色类型:拥有的角色 */
	public static final String OPR_ROLE_REL_01 = "01";
	/** 角色类型:可向下授权的角色 */
	public static final String OPR_ROLE_REL_00 = "00";
	
	/** 操作员状态:注销*/
	public static final String OPR_STS_00 = "00";
	/** 操作员状态:正常 */
	public static final String OPR_STS_01 = "01";
	/** 操作员状态:锁定*/
	public static final String OPR_STS_02 = "02";
	
	/** 角色状态:正常 */
	public static final String ROLE_STS_01 = "01";
	/** 角色状态:注销 */
	public static final String ROLE_STS_00 = "00";
	
	/** 菜单项状态:正常 */
	public static final String ITEM_STS_01 = "01";
	/** 菜单项状态:注销 */
	public static final String ITEM_STS_00 = "00";
	
	/**最高机构编号 */
	public static final String SYSTEM_OPR_ID = "0000";

}
