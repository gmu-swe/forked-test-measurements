package edu.gmu.swe.junit.measurement;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.*;

public class Premain {


    private static final HashMap<String, Set<String>> methodsToLogEntry = new HashMap<>();
    private static final HashMap<String, Set<String>> methodsToLogExit = new HashMap<>();

    static {
        methodsToLogEntry.put("org/apache/tools/ant/taskdefs/optional/junit/JUnitTask", Collections.singleton("executeAsForked")); //(Lorg/apache/tools/ant/taskdefs/optional/junit/JUnitTest;Lorg/apache/tools/ant/taskdefs/ExecuteWatchdog;Ljava/io/File;)Lorg/apache/tools/ant/taskdefs/optional/junit/JUnitTask$TestResultHolder;"));
        methodsToLogExit.put("org/apache/tools/ant/taskdefs/optional/junit/JUnitTask", Collections.singleton("executeAsForked")); //(Lorg/apache/tools/ant/taskdefs/optional/junit/JUnitTest;Lorg/apache/tools/ant/taskdefs/ExecuteWatchdog;Ljava/io/File;)Lorg/apache/tools/ant/taskdefs/optional/junit/JUnitTask$TestResultHolder;"));

        methodsToLogEntry.put("org/apache/maven/plugin/surefire/booterclient/ForkStarter", Collections.singleton("fork"));
        methodsToLogExit.put("org/apache/maven/plugin/surefire/booterclient/ForkStarter", Collections.singleton("fork"));

        methodsToLogEntry.put("org/gradle/api/internal/tasks/testing/worker/ForkingTestClassProcessor", Collections.singleton("processTestClass"));
        methodsToLogExit.put("org/gradle/api/internal/tasks/testing/worker/ForkingTestClassProcessor", Collections.singleton("stop"));
    }

    public static void log(String logLocation, String logType) {
        System.out.println("TESTPROFILER," + logLocation + "," + logType + "," + System.currentTimeMillis());
    }

    public static void premain(String args, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                try {
                    if (className != null && methodsToLogEntry.containsKey(className) || methodsToLogExit.containsKey(className)) {
                        System.out.println("Transform " + className);
                        Set<String> entry = methodsToLogEntry.get(className);
                        Set<String> exit = methodsToLogExit.get(className);
                        ClassReader cr = new ClassReader(classfileBuffer);
                        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7, cw) {
                            @Override
                            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                                if (entry.contains(name) || exit.contains(name)) {
                                    mv = new MethodVisitor(Opcodes.ASM7, mv) {
                                        @Override
                                        public void visitCode() {
                                            super.visitCode();
                                            if (entry.contains(name)) {
                                                super.visitLdcInsn(className + "." + name);
                                                super.visitLdcInsn("entry");
                                                super.visitMethodInsn(Opcodes.INVOKESTATIC, "edu/gmu/swe/junit/measurement/Premain", "log", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                                            }
                                        }

                                        @Override
                                        public void visitInsn(int opcode) {
                                            switch (opcode) {
                                                case Opcodes.RETURN:
                                                case Opcodes.ARETURN:
                                                case Opcodes.DRETURN:
                                                case Opcodes.FRETURN:
                                                case Opcodes.IRETURN:
                                                case Opcodes.LRETURN:
                                                    if (exit.contains(name)) {
                                                        super.visitLdcInsn(className + "." + name);
                                                        super.visitLdcInsn("exit");
                                                        super.visitMethodInsn(Opcodes.INVOKESTATIC, "edu/gmu/swe/junit/measurement/Premain", "log", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                                                    }
                                                    break;
                                            }
                                            super.visitInsn(opcode);
                                        }
                                    };
                                }
                                return mv;
                            }
                        };
                        cr.accept(cv, 0);
                        return cw.toByteArray();
                    }
                    return null;
                } catch (Throwable t) {
                    t.printStackTrace();
                    throw t;
                }
            }
        });
    }
}
