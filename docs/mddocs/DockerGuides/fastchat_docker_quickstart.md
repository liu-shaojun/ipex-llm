# FastChat Serving with IPEX-LLM on Intel GPUs via docker

This guide demonstrates how to run `FastChat` serving with `IPEX-LLM` on Intel GPUs via Docker.

## Install docker

Follow the instructions in this [guide](./docker_windows_gpu.md#linux) to install Docker on Linux.

## Build the Image
To build the `ipex-llm-serving-xpu` Docker image, use the following command:

```bash
cd docker/llm/serving/xpu/docker
docker build \
  --build-arg http_proxy=.. \
  --build-arg https_proxy=.. \
  --build-arg no_proxy=.. \
  --rm --no-cache -t intelanalytics/ipex-llm-serving-xpu:latest .
```

## Start Docker Container

 To map the `xpu` into the container, you need to specify `--device=/dev/dri` when booting the container. Change the `/path/to/models` to mount the models. 

```bash
#/bin/bash
export DOCKER_IMAGE=intelanalytics/ipex-llm-serving-xpu:latest
export CONTAINER_NAME=ipex-llm-serving-xpu-container
sudo docker run -itd \
        --net=host \
        --device=/dev/dri \
        -v /path/to/models:/llm/models \
        -e no_proxy=localhost,127.0.0.1 \
        --memory="32G" \
        --name=$CONTAINER_NAME \
        --shm-size="16g" \
        --entrypoint /bin/bash \
        $DOCKER_IMAGE
```

After the container is booted, you could get into the container through `docker exec`.

```bash
docker exec -it ipex-llm-serving-xpu-container /bin/bash
```


To verify the device is successfully mapped into the container, run `sycl-ls` to check the result. In a machine with Arc A770, the sampled output is:

```bash
root@arda-arc12:/# sycl-ls
[opencl:acc:0] Intel(R) FPGA Emulation Platform for OpenCL(TM), Intel(R) FPGA Emulation Device 1.2 [2023.16.7.0.21_160000]
[opencl:cpu:1] Intel(R) OpenCL, 13th Gen Intel(R) Core(TM) i9-13900K 3.0 [2023.16.7.0.21_160000]
[opencl:gpu:2] Intel(R) OpenCL Graphics, Intel(R) Arc(TM) A770 Graphics 3.0 [23.17.26241.33]
[ext_oneapi_level_zero:gpu:0] Intel(R) Level-Zero, Intel(R) Arc(TM) A770 Graphics 1.3 [1.3.26241]
```


## Running FastChat serving with IPEX-LLM on Intel GPU in Docker

For convenience, we have provided a script named `/llm/start-fastchat-service.sh` for you to start the service.  

However, the script only provide instructions for the most common scenarios. If this script doesn't meet your needs, you can always find the complete guidance for FastChat at [Serving using IPEX-LLM and FastChat](../Quickstart/fastchat_quickstart.md#2-start-the-service).

Before starting the service, you can refer to this [section](../Quickstart/install_linux_gpu.md#runtime-configurations) to setup our recommended runtime configurations.

Now we can start the FastChat service, you can use our provided script `/llm/start-fastchat-service.sh` like the following way:

```bash
# Only the MODEL_PATH needs to be set, other parameters have default values
export MODEL_PATH=YOUR_SELECTED_MODEL_PATH
export LOW_BIT_FORMAT=sym_int4
export CONTROLLER_HOST=localhost
export CONTROLLER_PORT=21001
export WORKER_HOST=localhost
export WORKER_PORT=21002
export API_HOST=localhost
export API_PORT=8000

# Use the default model_worker
bash /llm/start-fastchat-service.sh -w model_worker
```

If everything goes smoothly, the result should be similar to the following figure:

<a href="https://llm-assets.readthedocs.io/en/latest/_images/start-fastchat.png" target="_blank">
  <img src="https://llm-assets.readthedocs.io/en/latest/_images/start-fastchat.png" width=100%; />
</a>

By default, we are using the `ipex_llm_worker` as the backend engine.  You can also use `vLLM` as the backend engine.  Try the following examples:

```bash
# Only the MODEL_PATH needs to be set, other parameters have default values
export MODEL_PATH=YOUR_SELECTED_MODEL_PATH
export LOW_BIT_FORMAT=sym_int4
export CONTROLLER_HOST=localhost
export CONTROLLER_PORT=21001
export WORKER_HOST=localhost
export WORKER_PORT=21002
export API_HOST=localhost
export API_PORT=8000

# Use the default model_worker
bash /llm/start-fastchat-service.sh -w vllm_worker
```

The `vllm_worker` may start slowly than normal `ipex_llm_worker`.  The booted service should be similar to the following figure:

<a href="https://llm-assets.readthedocs.io/en/latest/_images/fastchat-vllm-worker.png" target="_blank">
  <img src="https://llm-assets.readthedocs.io/en/latest/_images/fastchat-vllm-worker.png" width=100%; />
</a>



> [!note]
> To verify/use the service booted by the script, follow the instructions in [this guide](../Quickstart/fastchat_quickstart.md#launch-restful-api-server).


After a request has been sent to the `openai_api_server`, the corresponding inference time latency can be found in the worker log as shown below:

<a href="https://llm-assets.readthedocs.io/en/latest/_images/fastchat-benchmark.png" target="_blank">
  <img src="https://llm-assets.readthedocs.io/en/latest/_images/fastchat-benchmark.png" width=100%; />
</a>
