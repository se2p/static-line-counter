package de.uni_passau.fim.se2.counter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class CounterClassVisitor extends ClassVisitor {

  private final Map<String, Integer> linesPerMethod;
  private final Map<String, Set<Integer>> lineNumbersPerMethod;

  public CounterClassVisitor(final int pAPI) {
    super(pAPI);
    linesPerMethod = new LinkedHashMap<>();
    lineNumbersPerMethod = new LinkedHashMap<>();
  }

  @Override
  public MethodVisitor visitMethod(
      final int pAccess,
      final String pName,
      final String pDescriptor,
      final String pSignature,
      final String[] pExceptions) {
    final MethodVisitor mv =
        super.visitMethod(pAccess, pName, pDescriptor, pSignature, pExceptions);
    return new CounterMethodVisitor(api, mv, this, pName, pDescriptor);
  }

  public Map<String, Integer> getLinesPerMethod() {
    return linesPerMethod;
  }

  public Map<String, Set<Integer>> getLineNumbersPerMethod() {
    return lineNumbersPerMethod;
  }

  void setLinesPerMethod(final String pMethodName, final int pLineCount) {
    linesPerMethod.put(pMethodName, pLineCount);
  }

  void setLineNumbersPerMethod(final String pMethodName, final Set<Integer> pLineNumbers) {
    lineNumbersPerMethod.put(pMethodName, pLineNumbers);
  }
}
