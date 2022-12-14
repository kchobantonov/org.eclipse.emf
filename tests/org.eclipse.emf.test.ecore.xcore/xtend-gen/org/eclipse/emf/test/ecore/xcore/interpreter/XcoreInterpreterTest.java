/**
 * Copyright (c) 2011-2012 Eclipse contributors and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.emf.test.ecore.xcore.interpreter;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.xcore.XPackage;
import org.eclipse.emf.test.ecore.xcore.XcoreStandaloneInjectorProvider;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(XcoreStandaloneInjectorProvider.class)
@SuppressWarnings("all")
public class XcoreInterpreterTest {
  @Inject
  private ParseHelper<XPackage> parse;

  @Inject
  private ValidationTestHelper validator;

  @Test
  public void testInterpretation() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo.bar");
      _builder.newLine();
      _builder.newLine();
      _builder.append("class Foo {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("op String doStuff(String msg) {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("return \"Foo says hi to \"+msg");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("Foo");
      final EClass fooClass = ((EClass) _eClassifier);
      final EObject foo = ePackage.getEFactoryInstance().create(fooClass);
      EOperation _head = IterableExtensions.<EOperation>head(fooClass.getEOperations());
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList("Bar");
      BasicEList<String> _basicEList = new BasicEList<String>(_newArrayList);
      Assert.assertEquals("Foo says hi to Bar", foo.eInvoke(_head, _basicEList));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testInterpretation_2() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo.bar");
      _builder.newLine();
      _builder.newLine();
      _builder.append("class Foo {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("op String call1(String msg) {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("return \"call1\"+call2(\"call1\"+msg)");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("op String call2(String msg) {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("return \"call2\"+msg");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("Foo");
      final EClass fooClass = ((EClass) _eClassifier);
      final EObject foo = ePackage.getEFactoryInstance().create(fooClass);
      EOperation _head = IterableExtensions.<EOperation>head(fooClass.getEOperations());
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList("Bar");
      BasicEList<String> _basicEList = new BasicEList<String>(_newArrayList);
      Assert.assertEquals("call1call2call1Bar", foo.eInvoke(_head, _basicEList));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testFeatureAccessors() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo.bar");
      _builder.newLine();
      _builder.newLine();
      _builder.append("class Foo {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("String value");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("op void storeValue(String newValue) {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("value = newValue");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("op String fetchValue() {");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("return value");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("Foo");
      final EClass fooClass = ((EClass) _eClassifier);
      final EObject foo = ePackage.getEFactoryInstance().create(fooClass);
      EOperation _head = IterableExtensions.<EOperation>head(fooClass.getEOperations());
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList("Bar");
      BasicEList<String> _basicEList = new BasicEList<String>(_newArrayList);
      foo.eInvoke(_head, _basicEList);
      Assert.assertEquals("Bar", foo.eInvoke(fooClass.getEOperations().get(1), null));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testConversionDelegates() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo.bar ");
      _builder.newLine();
      _builder.newLine();
      _builder.append("type URI wraps org.eclipse.emf.common.util.URI ");
      _builder.newLine();
      _builder.append("create { if (it == null) null else org::eclipse::emf::common::util::URI::createURI(it) } ");
      _builder.newLine();
      _builder.append("convert { it?.toString  }");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("URI");
      final EDataType uriDataType = ((EDataType) _eClassifier);
      final String literal = "http://www.eclipse.org";
      Object _createFromString = ePackage.getEFactoryInstance().createFromString(uriDataType, literal);
      final URI uri = ((URI) _createFromString);
      Assert.assertEquals(literal, ePackage.getEFactoryInstance().convertToString(uriDataType, uri));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testSettingDelegates() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo.bar");
      _builder.newLine();
      _builder.append("class Foo");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("String name");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("String alias get { name}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("Foo");
      final EClass fooClass = ((EClass) _eClassifier);
      final EObject foo = ePackage.getEFactoryInstance().create(fooClass);
      foo.eSet(fooClass.getEStructuralFeature("name"), "Sven");
      Assert.assertEquals("Sven", foo.eGet(fooClass.getEStructuralFeature("alias")));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testBooleanSettingDelegates() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo.bar");
      _builder.newLine();
      _builder.append("class Foo");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("boolean value");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("boolean oppositeValue get { !value }");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("Foo");
      final EClass fooClass = ((EClass) _eClassifier);
      final EObject foo = ePackage.getEFactoryInstance().create(fooClass);
      Assert.assertEquals(Boolean.valueOf(true), foo.eGet(fooClass.getEStructuralFeature("oppositeValue")));
      foo.eSet(fooClass.getEStructuralFeature("value"), Boolean.TRUE);
      Assert.assertEquals(Boolean.valueOf(false), foo.eGet(fooClass.getEStructuralFeature("oppositeValue")));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testInstanceOfAndCast() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package foo.bar");
      _builder.newLine();
      _builder.append("class Foo ");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("String bar get { if (this instanceof Bar) (this as Bar).value }");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.append("class Bar extends Foo");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("String value");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("Foo");
      final EClass fooClass = ((EClass) _eClassifier);
      final EObject foo = ePackage.getEFactoryInstance().create(fooClass);
      EClassifier _eClassifier_1 = ePackage.getEClassifier("Bar");
      final EClass barClass = ((EClass) _eClassifier_1);
      final EObject bar = ePackage.getEFactoryInstance().create(barClass);
      bar.eSet(barClass.getEStructuralFeature("value"), "Sven");
      Assert.assertEquals(null, foo.eGet(fooClass.getEStructuralFeature("bar")));
      Assert.assertEquals("Sven", bar.eGet(fooClass.getEStructuralFeature("bar")));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testEnumJDK14() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@GenModel(complianceLevel=\"1.4\")");
      _builder.newLine();
      _builder.append("package foo.bar14");
      _builder.newLine();
      _builder.append("enum NodeKind { Singleton Root Intermediate Leaf }");
      _builder.newLine();
      _builder.append("class Node");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("refers Node parent opposite children");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("contains Node[0..*] children opposite parent");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("op boolean hasChildren() { !children.empty }");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("transient volatile derived readonly NodeKind nodeKind");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("get");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("if (hasChildren()) {if (parent == null) {NodeKind::ROOT_LITERAL} else {NodeKind.INTERMEDIATE_LITERAL}}");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("else {if (parent == null) {NodeKind::SINGLETON_LITERAL} else {NodeKind::LEAF_LITERAL}}");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("NodeKind");
      final EEnum nodeKindEnum = ((EEnum) _eClassifier);
      EClassifier _eClassifier_1 = ePackage.getEClassifier("Node");
      final EClass nodeClass = ((EClass) _eClassifier_1);
      final EObject node = ePackage.getEFactoryInstance().create(nodeClass);
      Assert.assertEquals(nodeKindEnum.getEEnumLiteral("Singleton"), node.eGet(nodeClass.getEStructuralFeature("nodeKind")));
      final EObject childNode = ePackage.getEFactoryInstance().create(nodeClass);
      Object _eGet = node.eGet(nodeClass.getEStructuralFeature("children"));
      ((List<EObject>) _eGet).add(childNode);
      Assert.assertEquals(nodeKindEnum.getEEnumLiteral("Root"), node.eGet(nodeClass.getEStructuralFeature("nodeKind")));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testEnumJDK50() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@GenModel(complianceLevel=\"5.0\")");
      _builder.newLine();
      _builder.append("package foo.bar15");
      _builder.newLine();
      _builder.append("enum NodeKind { Singleton Root Intermediate Leaf }");
      _builder.newLine();
      _builder.append("class Node");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("refers Node parent opposite children");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("contains Node[0..*] children opposite parent");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("op boolean hasChildren() { !children.empty }");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("transient volatile derived readonly NodeKind nodeKind");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("get");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("if (hasChildren()) {if (parent == null) {NodeKind::ROOT} else {NodeKind.INTERMEDIATE}}");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("else {if (parent == null) {NodeKind::SINGLETON} else {NodeKind::LEAF}}");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final XPackage pack = this.parse.parse(_builder);
      this.validator.assertNoErrors(pack);
      EObject _get = pack.eResource().getContents().get(2);
      final EPackage ePackage = ((EPackage) _get);
      EClassifier _eClassifier = ePackage.getEClassifier("NodeKind");
      final EEnum nodeKindEnum = ((EEnum) _eClassifier);
      EClassifier _eClassifier_1 = ePackage.getEClassifier("Node");
      final EClass nodeClass = ((EClass) _eClassifier_1);
      final EObject node = ePackage.getEFactoryInstance().create(nodeClass);
      Assert.assertEquals(nodeKindEnum.getEEnumLiteral("Singleton"), node.eGet(nodeClass.getEStructuralFeature("nodeKind")));
      final EObject childNode = ePackage.getEFactoryInstance().create(nodeClass);
      Object _eGet = node.eGet(nodeClass.getEStructuralFeature("children"));
      ((List<EObject>) _eGet).add(childNode);
      Assert.assertEquals(nodeKindEnum.getEEnumLiteral("Root"), node.eGet(nodeClass.getEStructuralFeature("nodeKind")));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
