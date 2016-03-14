package com.github.mikegehard

import io.damo.kspec.JUnitDescriptionRunner
import io.damo.kspec.Spec
import io.damo.kspec.Test
import org.junit.runners.model.FrameworkMethod
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

class SpringKSpecClassRunner(val specificationClass: Class<Any>): SpringJUnit4ClassRunner(specificationClass) {
    override fun getChildren(): MutableList<FrameworkMethod> {
        if (Spec::class.java.isAssignableFrom(specificationClass) && !specificationClass.isLocalClass) {
            val spec = specificationClass.newInstance() as Spec

            // need to figure out how to map JUnitDescriptionRunners to FrameworkMethods??
            return spec.descriptions
                    .map { JUnitDescriptionRunner(specificationClass, it, spec.beforeBlock, spec.afterBlock) }
                    .toMutableList()
        }

        return arrayListOf()
    }
}
