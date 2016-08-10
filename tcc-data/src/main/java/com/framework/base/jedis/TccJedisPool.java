package com.framework.base.jedis;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.JedisURIHelper;
import redis.clients.util.Pool;

import java.net.URI;

public class TccJedisPool extends Pool<Jedis> {

    public TccJedisPool() {
        this(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final String host) {
        this(poolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null,
                Protocol.DEFAULT_DATABASE, null);
    }

    public TccJedisPool(String host, int port) {
        this(new GenericObjectPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT, null,
                Protocol.DEFAULT_DATABASE, null);
    }

    public TccJedisPool(final String host) {
        URI uri = URI.create(host);
        if (JedisURIHelper.isValid(uri)) {
            String h = uri.getHost();
            int port = uri.getPort();
            String password = JedisURIHelper.getPassword(uri);
            int database = JedisURIHelper.getDBIndex(uri);
            this.internalPool = new GenericObjectPool<Jedis>(new TccJedisFactory(h, port,
                    Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, password, database, null),
                    new GenericObjectPoolConfig());
        } else {
            this.internalPool = new GenericObjectPool<Jedis>(new TccJedisFactory(host,
                    Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, null,
                    Protocol.DEFAULT_DATABASE, null), new GenericObjectPoolConfig());
        }
    }

    public TccJedisPool(final URI uri) {
        this(new GenericObjectPoolConfig(), uri, Protocol.DEFAULT_TIMEOUT);
    }

    public TccJedisPool(final URI uri, final int timeout) {
        this(new GenericObjectPoolConfig(), uri, timeout);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                        int timeout, final String password) {
        this(poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE, null);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port) {
        this(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE, null);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                        final int timeout) {
        this(poolConfig, host, port, timeout, null, Protocol.DEFAULT_DATABASE, null);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                        int timeout, final String password, final int database) {
        this(poolConfig, host, port, timeout, password, database, null);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                        int timeout, final String password, final int database, final String clientName) {
        this(poolConfig, host, port, timeout, timeout, password, database, clientName);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                        final int connectionTimeout, final int soTimeout, final String password, final int database,
                        final String clientName) {
        super(poolConfig, new TccJedisFactory(host, port, connectionTimeout, soTimeout, password,
                database, clientName));
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final URI uri) {
        this(poolConfig, uri, Protocol.DEFAULT_TIMEOUT);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final URI uri, final int timeout) {
        this(poolConfig, uri, timeout, timeout);
    }

    public TccJedisPool(final GenericObjectPoolConfig poolConfig, final URI uri,
                        final int connectionTimeout, final int soTimeout) {
        super(poolConfig, new TccJedisFactory(uri, connectionTimeout, soTimeout, null));
    }

    @Override
    public Jedis getResource() {
        Jedis jedis = super.getResource();
        jedis.setDataSource(this);
        return jedis;
    }

    /**
     * @deprecated starting from Jedis 3.0 this method will not be exposed. Resource cleanup should be
     *             done using @see {@link Jedis#close()}
     */
    @Override
    @Deprecated
    public void returnBrokenResource(final Jedis resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }

    /**
     * @deprecated starting from Jedis 3.0 this method will not be exposed. Resource cleanup should be
     *             done using @see {@link Jedis#close()}
     */
    @Override
    @Deprecated
    public void returnResource(final Jedis resource) {
        if (resource != null) {
            try {
                resource.resetState();
                returnResourceObject(resource);
            } catch (Exception e) {
                returnBrokenResource(resource);
                throw new JedisException("Could not return the resource to the pool", e);
            }
        }
    }
}

