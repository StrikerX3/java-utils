package com.ivan.utils.lang.unsigned;

/**
 * Tipo de valores primitivos sem sinal (unsigned).
 * 
 * @author Ivan
 */
public abstract class Unsigned extends Number {
	private static final long serialVersionUID = 9154293144098453937L;

	public abstract Unsigned add(Number num);

	public abstract Unsigned sub(Number num);

	public abstract Unsigned mult(Number num);

	public abstract Unsigned div(Number num);

	public abstract Unsigned rem(Number num);

	public abstract boolean lt(Number num);

	public abstract boolean lte(Number num);

	public abstract boolean gt(Number num);

	public abstract boolean gte(Number num);

	public abstract boolean eq(Number num);

	public abstract boolean neq(Number num);
}