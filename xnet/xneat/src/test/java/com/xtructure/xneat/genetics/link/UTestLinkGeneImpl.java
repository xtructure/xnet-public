/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.genetics.link;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.link.impl.AbstractLinkGene;
import com.xtructure.xneat.genetics.link.impl.LinkGeneImpl;
import com.xtructure.xutil.id.XId;

@Test(groups = { "xml:xneat" })
public class UTestLinkGeneImpl {
	private static final LinkGeneConfiguration	CONFIGURATION;
	private static final LinkGeneImpl			LINK_GENE;
	private static final XId					SRC_ID, TGT_ID;
	static {
		SRC_ID = XId.newId("node", 0);
		TGT_ID = XId.newId("node", 1);
		CONFIGURATION = LinkGeneConfiguration.builder(XId.newId("UTestLinkGeneImpl", 0)).newInstance();
		LINK_GENE = new LinkGeneImpl(0, SRC_ID, TGT_ID, CONFIGURATION);
	}

	public void constructorSucceeds() {
		assertThat("",//
				LINK_GENE, isNotNull());
		assertThat("",//
				new LinkGeneImpl(0, LINK_GENE), isNotNull());
	}

	public void getBaseSourceTargetIdReturnsExpectedId() {
		assertThat("",//
				LINK_GENE.getBaseId(), isEqualTo(AbstractLinkGene.LINK_BASE_ID));
		assertThat("",//
				LINK_GENE.getSourceId(), isEqualTo(SRC_ID));
		assertThat("",//
				LINK_GENE.getTargetId(), isEqualTo(TGT_ID));
	}

	public void getSetWeightBehaveAsExpected() {
		assertThat("",//
				LINK_GENE.getWeight(), isEqualTo(LINK_GENE.getParameter(LinkGene.WEIGHT_ID)));
		Double newWeight = (Double) CONFIGURATION.getParameter(LinkGene.WEIGHT_ID).newField().getValue();
		LINK_GENE.setWeight(newWeight);
		assertThat("",//
				LINK_GENE.getWeight(), isEqualTo(newWeight));
	}

	public void toStringReturnsExpectedString() {
		assertThat("",//
				LINK_GENE.toString(),//
				isEqualTo(new ToStringBuilder(LINK_GENE, ToStringStyle.SHORT_PREFIX_STYLE)//
						.append("id", LINK_GENE.getId())//
						.append("source", LINK_GENE.getSourceId())//
						.append("target", LINK_GENE.getTargetId())//
						.append("inn", LINK_GENE.getInnovation())//
						.append("fields", LINK_GENE.getFieldMap())//
						.toString()));
	}

	public void validateSucceeds() {
		LINK_GENE.validate();
	}
}
