/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.intel.analytics.bigdl.friesian.serving.recall.faiss.swighnswlib;

public class BufferedIOWriter extends IOWriter {
  private transient long swigCPtr;

  protected BufferedIOWriter(long cPtr, boolean cMemoryOwn) {
    super(swigfaissJNI.BufferedIOWriter_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BufferedIOWriter obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_BufferedIOWriter(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void setWriter(IOWriter value) {
    swigfaissJNI.BufferedIOWriter_writer_set(swigCPtr, this, IOWriter.getCPtr(value), value);
  }

  public IOWriter getWriter() {
    long cPtr = swigfaissJNI.BufferedIOWriter_writer_get(swigCPtr, this);
    return (cPtr == 0) ? null : new IOWriter(cPtr, false);
  }

  public void setBsz(long value) {
    swigfaissJNI.BufferedIOWriter_bsz_set(swigCPtr, this, value);
  }

  public long getBsz() {
    return swigfaissJNI.BufferedIOWriter_bsz_get(swigCPtr, this);
  }

  public void setOfs(long value) {
    swigfaissJNI.BufferedIOWriter_ofs_set(swigCPtr, this, value);
  }

  public long getOfs() {
    return swigfaissJNI.BufferedIOWriter_ofs_get(swigCPtr, this);
  }

  public void setB0(long value) {
    swigfaissJNI.BufferedIOWriter_b0_set(swigCPtr, this, value);
  }

  public long getB0() {
    return swigfaissJNI.BufferedIOWriter_b0_get(swigCPtr, this);
  }

  public void setBuffer(CharVector value) {
    swigfaissJNI.BufferedIOWriter_buffer_set(swigCPtr, this, CharVector.getCPtr(value), value);
  }

  public CharVector getBuffer() {
    long cPtr = swigfaissJNI.BufferedIOWriter_buffer_get(swigCPtr, this);
    return (cPtr == 0) ? null : new CharVector(cPtr, false);
  }

  public BufferedIOWriter(IOWriter writer, long bsz) {
    this(swigfaissJNI.new_BufferedIOWriter(IOWriter.getCPtr(writer), writer, bsz), true);
  }

}