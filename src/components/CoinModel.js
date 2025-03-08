import React from "react";
import { useGLTF } from "@react-three/drei";

export function CoinModel(props) {
  const { scene } = useGLTF("/models/coin_new.glb"); // Load coin model
  return <primitive object={scene} {...props} />;
}
