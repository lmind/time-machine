package com.github.lmind.timemachine;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

public class TimeMachineTransformer implements ClassFileTransformer {
	
	private long baseTimeMillis;
	
	private long startNano;

	public TimeMachineTransformer(long baseTimeMillis, long startNano) {
		super();
		this.baseTimeMillis = baseTimeMillis;
		this.startNano = startNano;
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		
		if (className.equals("java/lang/System")) {
			return t();
		}
		
		return null;
	}
	
	private byte[] t() {
		ClassPool pool = ClassPool.getDefault();
		try {
			CtClass ct = pool.get("java.lang.System");
			for (CtMethod m : ct.getMethods()) {
				if (m.getName().equals("currentTimeMillis")) {
					System.out.println(m.getSignature());
					ct.removeMethod(m);
//					m.setBody("return 1l;");
				}
			}
			
			CtMethod mm = new CtMethod(pool.get("long"), "currentTimeMillis", null, ct);
			
			String body = String.format("return com.github.lmind.timemachine.TimeMachineSystem.currentTimeMillis(" + baseTimeMillis + "l, " + startNano + "l);");
			
			mm.setBody(body);
			mm.setModifiers(Modifier.STATIC | Modifier.PUBLIC);
			ct.addMethod(mm);
			
			ct.rebuildClassFile();
			
			return ct.toBytecode();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
