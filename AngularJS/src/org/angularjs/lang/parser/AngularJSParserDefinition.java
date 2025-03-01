// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.angularjs.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.javascript.JavascriptParserDefinition;
import com.intellij.lang.javascript.parsing.JavaScriptParser;
import com.intellij.lang.javascript.types.JSFileElementType;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.tree.IFileElementType;
import org.angularjs.lang.AngularJSLanguage;
import org.angularjs.lang.lexer.AngularJSLexer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dennis.Ushakov
 */
public final class AngularJSParserDefinition extends JavascriptParserDefinition {
  public static final IFileElementType FILE = JSFileElementType.create(AngularJSLanguage.INSTANCE);

  @Override
  public @NotNull Lexer createLexer(Project project) {
    return new AngularJSLexer();
  }

  @Override
  public @NotNull PsiParser createParser(Project project) {
    return new AngularParser();
  }

  @Override
  public @NotNull JavaScriptParser createJSParser(@NotNull PsiBuilder builder) {
    return new AngularJSParser(builder);
  }

  @Override
  public @NotNull IFileElementType getFileNodeType() {
    return FILE;
  }
}
