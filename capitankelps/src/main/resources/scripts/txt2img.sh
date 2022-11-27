echo "Text to image script!"
echo "Input text: $1"

USER_HOME=/home/sergio

echo "Setup python env"
source $USER_HOME/venv_python3_8_12/bin/activate

echo "change directory to stability ai folder"
cd $USER_HOME/Projects/Stability-AI/stablediffusion

echo "Run "

MODEL_PATH=/media/sergio/models/stable_diffusion_v2
USE_512=true
if [ "$USE_512" = true ] ; then
  python scripts/txt2img.py --prompt "$1" --ckpt $MODEL_PATH/512-base-ema.ckpt --n_samples 1 
else
  python scripts/txt2img.py --prompt "$1" --ckpt $MODEL_PATH/768-v-ema.ckpt  --config configs/stable-diffusion/v2-inference-v.yaml --H 768 --W 768  --n_samples 1 
fi


