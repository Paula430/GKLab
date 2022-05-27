let canvas = document.getElementById("glcanvas");

const scene = new THREE.Scene({ color: 0xfff });

const camera = new THREE.PerspectiveCamera(
  100,
  window.innerWidth / window.innerHeight,
  1,
  1000
);

const renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });

renderer.setSize(window.innerWidth, window.innerHeight);

document.body.appendChild(renderer.domElement);

var light;
light = new THREE.DirectionalLight();
light.position.set(-9, 7, 5);
camera.add(light);

scene.add(camera);

const color = new THREE.MeshPhongMaterial({
  color: 0x3c232c,
});

const base1 = new THREE.Mesh(
  new THREE.CylinderGeometry(1, 1, 0.15, 100),
  color
);
const base2 = new THREE.Mesh(
  new THREE.CylinderGeometry(0.9, 0.9, 0.25, 100),
  color
);
base2.position.y = 0.1;

let AllBase = new THREE.Group();
AllBase.add(base1);
AllBase.add(base2);

const part1 = new THREE.Mesh(
  new THREE.CylinderGeometry(0.4, 0.9, 2, 10),
  color
);

part1.position.set(0, 1, 0);
AllBase.add(part1);

const part2 = new THREE.Mesh(
  new THREE.CylinderGeometry(0.7, 0.8, 0.15, 100),
  color
);

part2.position.y = 1.9;

const ball = new THREE.SphereGeometry(
  Math.PI / 5.5,
  100,
  100,
  Math.PI,
  2 * Math.PI,
  0,
  Math.PI
);

color.side = THREE.DoubleSide;
const part3 = new THREE.Mesh(ball, color);

part3.position.set(0, 2.5, 0);

AllBase.add(part3);
AllBase.add(part2);
AllBase.position.set(0, -4, 0);
AllBase.scale.set(3, 4, 1);
scene.add(AllBase);

camera.position.z = 10;
renderer.render(scene, camera);
