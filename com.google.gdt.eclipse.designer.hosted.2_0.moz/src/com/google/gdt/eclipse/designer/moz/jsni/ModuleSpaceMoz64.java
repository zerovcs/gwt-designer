/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gdt.eclipse.designer.moz.jsni;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.shell.CompilingClassLoader;
import com.google.gwt.dev.shell.JsValue;
import com.google.gwt.dev.shell.JsValueGlue;
import com.google.gwt.dev.shell.ModuleSpace;
import com.google.gwt.dev.shell.ModuleSpaceHost;

/**
 * An implementation of {@link com.google.gwt.dev.shell.ModuleSpace} for
 * Mozilla.
 */
public class ModuleSpaceMoz64 extends ModuleSpace {

  private final long window;

  /**
   * Constructs a browser interface for use with a Mozilla global window object.
   */
  public ModuleSpaceMoz64(ModuleSpaceHost host, long scriptGlobalObject,
      String moduleName) {
    super(host.getLogger(), host, moduleName);

    // Hang on to the parent window.
    //
    window = scriptGlobalObject;
  }

  @Override
  protected void doCreateNativeMethods(String jsni) {
    LowLevelMoz64.executeScriptWithInfo(window, jsni, "", 0);
  }
  @Override
  protected void createStaticDispatcher(TreeLogger logger) {
	String newScript = createNativeMethodInjector("__defineStatic", new String[] {"__arg0"}, "window.__static = __arg0;");
    LowLevelMoz64.executeScriptWithInfo(window, newScript, "", 0);
  }
  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.dev.shell.ModuleSpace#dispose()
   */
  @Override
  public void dispose() {
    super.dispose();
  }

  /**
   * Invokes a native JavaScript function.
   * 
   * @param name the name of the function to invoke
   * @param jthis the function's 'this' context
   * @param types the type of each argument
   * @param args the arguments to be passed
   * @return the return value as a Object.
   */
  @Override
  protected JsValue doInvoke(String name, Object jthis, Class<?>[] types,
      Object[] args) {
    CompilingClassLoader isolatedClassLoader = getIsolatedClassLoader();

    JsValueMoz64 jsthis = new JsValueMoz64();
    Class<?> jthisType = (jthis == null) ? Object.class : jthis.getClass();
    JsValueGlue.set(jsthis, isolatedClassLoader, jthisType, jthis);

    int argc = args.length;
    JsValueMoz64 argv[] = new JsValueMoz64[argc];
    long [] jsArgsInt = new long [argc];
    for (int i = 0; i < argc; ++i) {
      argv[i] = new JsValueMoz64();
      JsValueGlue.set(argv[i], isolatedClassLoader, types[i], args[i]);
      jsArgsInt[i] = argv[i].getJsRootedValue();
    }
    JsValueMoz64 returnVal = new JsValueMoz64();
    LowLevelMoz64.invoke(window, name, jsthis.getJsRootedValue(), jsArgsInt,
        returnVal.getJsRootedValue());
    return returnVal;
  }

  @Override
  protected Object getStaticDispatcher() {
    return new GeckoDispatchAdapter64(getIsolatedClassLoader());
  }
}
