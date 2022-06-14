package de.uni_passau.fim.se2.counter;

import com.google.errorprone.annotations.Var;
import java.util.LinkedHashSet;
import java.util.Set;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class CounterMethodVisitor extends MethodVisitor {

  private final CounterClassVisitor ccv;
  private final String methodName;
  @Var private int lineCount;
  private final Set<Integer> lineNumbers;

  CounterMethodVisitor(
      final int pAPI,
      final MethodVisitor pMethodVisitor,
      final CounterClassVisitor pCounterClassVisitor,
      final String pMethodName,
      final String pDescriptor) {
    super(pAPI, pMethodVisitor);
    ccv = pCounterClassVisitor;
    methodName = String.format("%s:%s", pMethodName, pDescriptor);
    lineCount = 0;
    lineNumbers = new LinkedHashSet<>();
  }

  @Override
  public void visitLineNumber(final int pLine, final Label pStart) {
    super.visitLineNumber(pLine, pStart);
    lineCount++;
    lineNumbers.add(pLine);
  }

  @Override
  public void visitEnd() {
    super.visitEnd();
    ccv.setLineNumbersPerMethod(methodName, lineNumbers);
    ccv.setLinesPerMethod(methodName, lineCount);
  }
}
