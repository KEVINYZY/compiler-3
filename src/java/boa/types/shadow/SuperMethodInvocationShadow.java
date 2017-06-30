/*
 * Copyright 2017, Robert Dyer, Kaushik Nimmala
 *                 and Bowling Green State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package boa.types.shadow;

import boa.compiler.ast.Call;
import boa.compiler.ast.expressions.Expression;
import boa.compiler.ast.Factor;
import boa.compiler.ast.Identifier;
import boa.compiler.ast.Node;
import boa.compiler.SymbolTable;
import boa.compiler.transforms.ASTFactory;
import boa.types.BoaInt;
import boa.types.BoaString;
import boa.types.BoaProtoList;
import boa.types.BoaShadowType;
import boa.types.proto.enums.ExpressionKindProtoMap;
import boa.types.proto.ExpressionProtoTuple;
import boa.types.proto.StatementProtoTuple;
import boa.types.proto.TypeProtoTuple;
/**
 * A shadow type for SuperMethodInvocation.
 * 
 * @author rdyer
 * @author kaushin
 */
public class SuperMethodInvocationShadow extends BoaShadowType  {
    /**
     * Construct a {@link SuperMethodInvocationShadow}.
     */
    public SuperMethodInvocationShadow() {
        super(new ExpressionProtoTuple());


        addShadow("qualifier", new ExpressionProtoTuple());
        addShadow("arguments",  new BoaProtoList(new ExpressionProtoTuple()));
        addShadow("type_infer", new ExpressionProtoTuple());
        addShadow("name", new BoaString());
    }

    /** {@inheritDoc} */
    @Override
	public Node lookupCodegen(final String name, final Factor node, final SymbolTable env) { 
        if ("qualifier".equals(name)) {
            // TODO
            return null;
        }

        if ("arguments".equals(name)) {
            // ${0}.expressions[1]
            return ASTFactory.createFactor("expressions",ASTFactory.createIntLiteral(1),new BoaProtoList(new ExpressionProtoTuple()), new ExpressionProtoTuple(),env);
        }

        if ("type_infer".equals(name)) {
            // TODO
           
             return null;
        }

        if ("name".equals(name)) {
            // ${0}.method
            return ASTFactory.createSelector( "method", new BoaString(),  env);
        }

        throw new RuntimeException("invalid shadow field: " + name);
    }

    /** {@inheritDoc} */
    @Override
    public Expression getKindExpression(final SymbolTable env) {
        return getKindExpression("ExpressionKind", "METHODCALL", new ExpressionKindProtoMap(), env);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "SuperMethodInvocation";
    }
}
