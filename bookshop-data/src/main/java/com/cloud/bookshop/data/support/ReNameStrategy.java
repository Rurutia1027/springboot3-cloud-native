package com.cloud.bookshop.data.support;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.spi.MetadataBuildingContext;

// we extend the super class to add a uniform prefix to all db associated mapping objects.
public class ReNameStrategy extends ImplicitNamingStrategyJpaCompliantImpl {
    @Override
    protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
        return super.toIdentifier("bs_" + stringForm, buildingContext);
    }
}
