package com.example.spark.opentsdb.tsdbclient.builder;

public class DataFormatException extends Exception
{
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8136189301503147448L;

    public DataFormatException()
	{
		super();
	}

	public DataFormatException(String s)
	{
		super(s);
	}
}
