package com.test;

/**
 * @Function: 机器类型
 * @Date: 2015-2-2 下午4:25:14
 * @author zhang
 */
public enum MachineType implements IEnumDescription {

	M_RF("RF", (byte) 0x10), M_IPL("IPL", (byte) 0x20), M_808("808",
			(byte) 0x30), M_Water_Oxygen("Water_Oxygen", (byte) 0x40);

	// 成员变量
	private String name;
	private byte index;

	private MachineType(String name, byte index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * 获取索引的枚举值名字
	 * 
	 * @param index
	 * @return
	 */
	public static String GetMachineTypeName(byte index) {
		for (MachineType m : MachineType.values()) {
			if (m.getIndex() == index) {
				return m.name;
			}
		}
		return null;
	}

	/**
	 * 获取索引的枚举�??
	 * 
	 * @param index
	 * @return
	 */
	public static MachineType GetMachineType(byte index) {
		for (MachineType m : MachineType.values()) {
			if (m.getIndex() == index) {
				return m;
			}
		}
		return null;
	}

	/**
	 * 获取名称的枚举�??
	 * 
	 * @param name
	 * @return
	 */
	public static MachineType GetMachineType(String name) {
		for (MachineType m : MachineType.values()) {
			if (m.getName() == name) {
				return m;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		// TODO 自动生成的方法存�?
		return this.name;
	}

	@Override
	public void setName(String name) {
		// TODO 自动生成的方法存�?
		this.name = name;
	}

	@Override
	public byte getIndex() {
		// TODO 自动生成的方法存�?
		return this.index;
	}

	@Override
	public void setIndex(byte index) {
		// TODO 自动生成的方法存�?
		this.index = index;
	}

}
