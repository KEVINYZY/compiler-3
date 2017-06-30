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

import java.util.*;

import boa.compiler.ast.Call;
import boa.compiler.ast.expressions.Expression;
import boa.compiler.ast.Factor;
import boa.compiler.ast.Identifier;
import boa.compiler.ast.Node;
import boa.compiler.SymbolTable;
import boa.compiler.transforms.ASTFactory;
import boa.types.BoaInt;
import boa.types.BoaProtoList;
import boa.types.BoaShadowType;
import boa.types.proto.enums.ExpressionKindProtoMap;
import boa.types.proto.ExpressionProtoTuple;
import boa.types.proto.StatementProtoTuple;
import boa.types.proto.TypeProtoTuple;

import boa.compiler.ast.statements.IfStatement;
import boa.compiler.ast.statements.Block;
/**
 * A shadow type for PrefixExpression.
 * 
 * @author rdyer
 * @author kaushin
 */
public class PrefixExpressionShadow extends BoaShadowType  {
    /**
     * Construct a {@link PrefixExpressionShadow}.
     */
    public PrefixExpressionShadow() {
        super(new ExpressionProtoTuple());

        addShadow("operand", new ExpressionProtoTuple());
    }

    /** {@inheritDoc} */
    @Override
	public Node lookupCodegen(final String name, final Factor node, final SymbolTable env) { 

        if ("operand".equals(name)) {
            // ${0}.expressions[0]
            return ASTFactory.createFactor("expressions",ASTFactory.createIntLiteral(0),new BoaProtoList(new ExpressionProtoTuple()), new ExpressionProtoTuple(),env);
        }


        throw new RuntimeException("invalid shadow field: " + name);
    }

    /** {@inheritDoc} */
    @Override
    public Expression getKindExpression(final SymbolTable env) {
        return getKindExpression("ExpressionKind", "IT_XOR", new ExpressionKindProtoMap(), env);
    }

    /** {@inheritDoc} */
    @Override
    public IfStatement getManytoOne(final SymbolTable env ,Block b) {
       
        // if(isboollit(${0})) b;
        final Expression tree = ASTFactory.createIdentifierExpr(boa.compiler.transforms.ShadowTypeEraser.NODE_ID, env, new ExpressionProtoTuple());


        IfStatement ifstmt = new IfStatement(ASTFactory.createCallExpr("isprefix", env, new ExpressionProtoTuple(), tree),b);
        return ifstmt ;   
    }

  

    /** {@inheritDoc} */
    @Override
    public List<Expression> getOneToMany(final SymbolTable env) {
        List<Expression> prefixList = new ArrayList<Expression>(); 
               
        prefixList.add(getKindExpression("ExpressionKind", "BIT_NOT", new ExpressionKindProtoMap(), env));
        prefixList.add(getKindExpression("ExpressionKind", "OP_DEC", new ExpressionKindProtoMap(), env));
        prefixList.add(getKindExpression("ExpressionKind", "OP_INC", new ExpressionKindProtoMap(), env));
        prefixList.add(getKindExpression("ExpressionKind", "OP_SUB", new ExpressionKindProtoMap(), env));
        prefixList.add(getKindExpression("ExpressionKind", "LOGICAL_NOT", new ExpressionKindProtoMap(), env));
        prefixList.add(getKindExpression("ExpressionKind", "OP_ADD", new ExpressionKindProtoMap(), env));
                
        return prefixList;  
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "PrefixExpression";
    }
}
