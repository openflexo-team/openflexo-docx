/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Excelconnector, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.technologyadapter.docx.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.doc.FlexoDocElement;
import org.openflexo.technologyadapter.docx.AbstractTestDocX;
import org.openflexo.technologyadapter.docx.DocXTechnologyAdapter;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

@RunWith(OrderedRunner.class)
public class TestMSWordIdentifiersPersistency extends AbstractTestDocX {
	protected static final Logger logger = Logger.getLogger(TestMSWordIdentifiersPersistency.class.getPackage().getName());

	private static DocXDocument step1;
	private static DocXDocument step2;
	private static DocXDocument step3;
	private static DocXDocument step4;
	private static DocXDocument step5;
	private static DocXDocument step6;

	@AfterClass
	public static void tearDownClass() {
		unloadAndDelete(step1);
		unloadAndDelete(step2);
		unloadAndDelete(step3);
		unloadAndDelete(step4);
		unloadAndDelete(step5);
		unloadAndDelete(step6);
		step1 = null;
		step2 = null;
		step3 = null;
		step4 = null;
		step5 = null;
		step6 = null;

		deleteProject();
		deleteTestResourceCenters();
		unloadServiceManager();
	}

	@Test
	@TestOrder(1)
	public void testInitializeServiceManager() throws Exception {
		instanciateTestServiceManagerForDocX(IdentifierManagementStrategy.ParaId);
	}

	/*@Test
	@TestOrder(3)
	public void testDocXLoading() {
		DocXTechnologyAdapter technologicalAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				DocXTechnologyAdapter.class);
	
		for (FlexoResourceCenter<?> resourceCenter : serviceManager.getResourceCenterService().getResourceCenters()) {
			DocXDocumentRepository docXRepository = resourceCenter.getRepository(DocXDocumentRepository.class, technologicalAdapter);
			assertNotNull(docXRepository);
			Collection<DocXDocumentResource> documents = docXRepository.getAllResources();
			for (DocXDocumentResource docResource : documents) {
				try {
					docResource.loadResourceData(null);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ResourceLoadingCancelledException e) {
					e.printStackTrace();
				} catch (FlexoException e) {
					e.printStackTrace();
				}
				assertNotNull(docResource.getLoadedResourceData());
				System.out.println("URI of document: " + docResource.getURI());
			}
		}
	}*/

	@Test
	@TestOrder(4)
	public void testStep1() {

		step1 = getDocument("MSWordDocumentEdition/Step1.docx");

		System.out.println("Step1.docx:\n" + step1.debugStructuredContents());

		assertEquals(13, step1.getElements().size());

		// DocXParagraph titleParagraph = (DocXParagraph) simpleDocument.getElements().get(0);

	}

	// Same document after a SaveAs in Microsoft Word
	@Test
	@TestOrder(5)
	public void testStep2() {

		step2 = getDocument("MSWordDocumentEdition/Step2.docx");

		System.out.println("Step2.docx:\n" + step2.debugStructuredContents());

		assertEquals(13, step2.getElements().size());
		assertEquals(step1.getElements().size(), step2.getElements().size());

		for (int i = 0; i < step1.getElements().size(); i++) {
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element1 = step1.getElements().get(i);
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element2 = step2.getElements().get(i);
			assertEquals(element1.getIdentifier(), element2.getIdentifier());
		}

	}

	// Same document after text added to an existing paragraph and a SaveAs in Microsoft Word
	@Test
	@TestOrder(6)
	public void testStep3() {

		step3 = getDocument("MSWordDocumentEdition/Step3.docx");

		System.out.println("Step3.docx:\n" + step3.debugStructuredContents());

		assertEquals(13, step3.getElements().size());
		assertEquals(step1.getElements().size(), step3.getElements().size());

		for (int i = 0; i < step1.getElements().size(); i++) {
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element1 = step1.getElements().get(i);
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element3 = step3.getElements().get(i);
			assertEquals(element1.getIdentifier(), element3.getIdentifier());
		}

	}

	// Same document after paragraph insertion and SaveAs in Microsoft Word
	@Test
	@TestOrder(7)
	public void testStep4() {

		step4 = getDocument("MSWordDocumentEdition/Step4.docx");

		System.out.println("Step4.docx:\n" + step4.debugStructuredContents());

		assertEquals(16, step4.getElements().size());

		for (int i = 0; i < step1.getElements().size(); i++) {
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element1 = step1.getElements().get(i);
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element4 = step4.getElementWithIdentifier(element1.getIdentifier());
			assertNotNull(element4);
		}

	}

	// Same document after paragraph style changing and SaveAs in Microsoft Word
	@Test
	@TestOrder(8)
	public void testStep5() {

		step5 = getDocument("MSWordDocumentEdition/Step5.docx");

		System.out.println("Step5.docx:\n" + step5.debugStructuredContents());

		assertEquals(17, step5.getElements().size());

		for (int i = 0; i < step4.getElements().size(); i++) {
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element4 = step4.getElements().get(i);
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element5 = step5.getElementWithIdentifier(element4.getIdentifier());
			assertNotNull(element5);
		}

	}

	// This test does not work, it appears that Microsoft Word identifier management do not track contents moving in the document

	// Same document after some paragraph moving and SaveAs in Microsoft Word
	/*@Test
	@TestOrder(9)
	public void testStep6() {
	
		step6 = getDocument("MSWordDocumentEdition/Step6.docx");
	
		System.out.println("Step6.docx:\n" + step6.debugStructuredContents());
	
		assertEquals(16, step6.getElements().size());
		assertEquals(step5.getElements().size(), step6.getElements().size());
	
		for (int i = 0; i < step4.getElements().size(); i++) {
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element5 = step5.getElements().get(i);
			FlexoDocElement<DocXDocument, DocXTechnologyAdapter> element6 = step6.getElementWithIdentifier(element5.getIdentifier());
			assertNotNull(element6);
		}
	
	}*/

}
