package org.autoTdd.internal;

import java.util.Arrays;

import org.softwarefm.utilities.arrays.ArrayHelper;

public class Constraint extends EngineType {

	public static Constraint withResult(Constraint raw, Object result) {
		return new Constraint(result, raw.because, raw.inputs);
	}
	public static Constraint withInputs(Constraint raw, Object[] inputs) {
		return new Constraint(raw.result, raw.because, inputs);
	}

	private final Object result;
	private final Object because;
	private final Object[] inputs;

	public Constraint(Object result, Object because, Object[] inputs) {
		super(result== null ? null : result.getClass(), ArrayHelper.classArray(inputs));
		assertClassesMatchesInputClasses(inputs);
		assertClassMatchesResultClass(result);
		this.result = result;
		this.because = because;
		this.inputs = inputs;
	}

	public Object getResult() {
		return result;
	}

	public Object getBecause() {
		return because;
	}

	public Object[] getInputs() {
		return inputs;
	}

	@Override
	public String toString() {
		return "Constraint [result=" + result + ", because=" + because + ", inputs=" + Arrays.toString(inputs) + "]";
	}


}
